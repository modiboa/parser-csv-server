package com.fabre.parsecsv.service;

import com.fabre.parsecsv.model.Decision;
import com.fabre.parsecsv.model.DecisionSetup;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


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

    public String createCsvFrom(List<Decision> decisions) throws IOException {
        Writer writer = new StringWriter();

        CSVWriter csvWriter = new CSVWriter(writer,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        String[] headerRecord = buildHeader(decisions.get(0));
        csvWriter.writeNext(headerRecord);

        decisions.forEach(decision -> csvWriter.writeNext(buildContent(decision)));

        csvWriter.close();

        return writer.toString();
    }

    private String[] buildHeader(Decision decision) {
        List<String> headers = new ArrayList<>();
        headers.add("Id");
        for (int i = 1; i <= decision.getVars().size(); i++) {
            headers.add("Var" + i);
        }
        headers.add("Decision");
        return headers.stream().toArray(String[]::new);
    }

    private String[] buildContent(Decision decision) {
        List<String> content = new ArrayList<>();
        content.add(decision.getId());

        decision.getVars().forEach(integer -> content.add(integer.toString()));

        content.add(decision.getDecision().toString());
        return content.stream().toArray(String[]::new);
    }


}
