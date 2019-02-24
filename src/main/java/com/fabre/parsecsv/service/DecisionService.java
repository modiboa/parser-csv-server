package com.fabre.parsecsv.service;


import com.fabre.parsecsv.model.Decision;
import com.fabre.parsecsv.model.DecisionSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionService {

    @Autowired
    CsvParser csvParser;

    public String prepare(byte[] byteArray) throws IOException {
        DecisionSetup decisionSetup = csvParser.parser(byteArray);

        List<Decision> fixedList = new ArrayList<>();

        decisionSetup.getDecisions().forEach(decision -> {
            if (decision.getDecision() == 1) {
                fixedList.add(decision);
            } else {
                if (decision.checkRange(decisionSetup.getMax(), decisionSetup.getMin())) {
                    fixedList.add(decision);
                }
            }
        });

        return csvParser.createCsvFrom(fixedList);
    }
}
