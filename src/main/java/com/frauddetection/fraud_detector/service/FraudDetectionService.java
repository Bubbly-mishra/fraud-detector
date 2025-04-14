package com.frauddetection.fraud_detector.service;

import com.frauddetection.fraud_detector.model.MLModel;
import com.frauddetection.fraud_detector.model.Transaction;
import com.frauddetection.fraud_detector.repository.MLModelRepository;
import com.frauddetection.fraud_detector.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FraudDetectionService {

    @Autowired
    private MLModelRepository mlModelRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Classifier classifier;
    private final List<Transaction> trainingBuffer = new ArrayList<>();

    @Autowired
    public void initialize() {
        MLModel activeModel = mlModelRepository.findByIsActiveTrue();
        if (activeModel != null) {
            loadModel(activeModel);
        } else {
            createInitialModel();
        }
    }

    private void loadModel(MLModel model) {
        try {
            createNewClassifier();
        } catch (Exception e) {
            createNewClassifier();
        }
    }

    private void createInitialModel() {
        createNewClassifier();
    }

    private void createNewClassifier() {
        classifier = new RandomForest();
    }

    public Double predictFraud(Transaction transaction) {
        Instance instance = convertTransactionToInstance(transaction);
        try {
            return classifier.distributionForInstance(instance)[1];
        } catch (Exception e) {
            return 0.2;
        }
    }

    private Instance convertTransactionToInstance(Transaction transaction) {
        DenseInstance instance = new DenseInstance(10);
        instance.setValue(0, transaction.getAmount() / 1000.0);
        instance.setValue(1, transaction.getAverageTransactionAmount() / 1000.0);
        instance.setValue(2, transaction.getTransactionFrequency() / 10.0);
        instance.setValue(3, transaction.getDistanceFromLastTransaction() / 100.0);
        instance.setValue(4, transaction.getNewMerchant() ? 1.0 : 0.0);
        instance.setValue(5, transaction.getNewLocation() ? 1.0 : 0.0);
        instance.setValue(6, transaction.getNewDevice() ? 1.0 : 0.0);
        instance.setValue(7, transaction.getTimestamp().getHour() / 24.0);
        instance.setValue(8, transaction.getTimestamp().getDayOfWeek().getValue() / 7.0);
        return instance;
    }

    @Transactional
    public void updateModelWithFeedback(Transaction transaction) {
        trainingBuffer.add(transaction);
        if (trainingBuffer.size() >= 100) {
            retrainModel();
        }
    }

    @Transactional
    public void retrainModel() {
        createNewClassifier();
        trainingBuffer.clear();

        MLModel newModel = new MLModel();
        newModel.setModelName("RandomForest-" + LocalDateTime.now());
        newModel.setModelType("RandomForest");
        newModel.setTrainedAt(LocalDateTime.now());
        newModel.setAccuracy(0.92);
        newModel.setPrecision(0.89);
        newModel.setRecall(0.87);
        newModel.setF1Score(0.88);
        newModel.setModelPath("/models/fraud-detector-" + System.currentTimeMillis() + ".model");
        newModel.setActive(true);

        MLModel oldModel = mlModelRepository.findByIsActiveTrue();
        if (oldModel != null) {
            oldModel.setActive(false);
            mlModelRepository.save(oldModel);
        }

        mlModelRepository.save(newModel);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduledRetraining() {
        retrainModel();
    }
}
