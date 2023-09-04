package com.tonio.spring6restmvc.service;

import com.tonio.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCSV(File csvFile) throws FileNotFoundException;
}
