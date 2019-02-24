package com.fabre.parsecsv.service;

import com.fabre.parsecsv.model.Decision;
import com.fabre.parsecsv.model.DecisionSetup;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


@Service
public class CsvParser {

    public DecisionSetup parser(byte[] bytes) throws IOException {

        Reader reader = new StringReader(new String(bytes));

        CSVReader csvReader = new CSVReader(reader);

        int headerCount = csvReader.readNext().length;

        String[] nextRecord = null;
        DecisionSetup decisionSetup = new DecisionSetup();
        while ((nextRecord = csvReader.readNext()) != null) {
            Decision decision = new Decision();
            decision.setId(nextRecord[0]);
            decision.setDecision(Integer.valueOf(nextRecord[headerCount - 1]));


            //Setting var list
            for (int i = 1; i < headerCount - 1; i++) {
                int var = Integer.valueOf(nextRecord[i]);
                decision.addVar(var);

                //Update FMIN and FMAX
                if (decision.getDecision() == 1) {
                    if (var > decisionSetup.getMax()) {
                        decisionSetup.setMax(var);
                    }
                    if (var < decisionSetup.getMin()) {
                        decisionSetup.setMin(var);
                    }
                }
            }

            decisionSetup.addDecision(decision);
        }

        return decisionSetup;
    }
}
