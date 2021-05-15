package com.example.landcover.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;

@RestController
public class LandCoverController {
    @ApiOperation(value = "Returns predicted matrix")
    @RequestMapping(value = "/predict/{rowNum}/{colNum}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public int[][] predictArray(@PathVariable("rowNum") @ApiParam(value = "Row Number", required = true) int rowNum,
                                @PathVariable("colNum") @ApiParam(value = "Column Number", required = true) int colNum) throws IOException {
        String fileName = "static/output.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file.getAbsolutePath())));
        int rows = 145;
        int columns = 145;
        float [][] myArray = new float[rows][columns];
        while(sc.hasNextLine()) {
            for (int i=0; i<myArray.length; i++) {
                String[] line = sc.nextLine().trim().split(",");
                for (int j=0; j<line.length; j++) {
                    myArray[i][j] = Float.parseFloat(line[j]);
                }
            }
        }
        if(rowNum == 0 || rowNum == 1) {
            rowNum = 2;
        }
        else if(rowNum == 143 || rowNum == 144) {
            rowNum = 142;
        }
        if(colNum == 0 || colNum == 1) {
            colNum = 2;
        }
        if(colNum == 143 || colNum == 144) {
            colNum = 142;
        }
        int [][] arr = new int[5][5];
        for(int i=rowNum - 2;i<rowNum + 2;i++) {
            for(int j=colNum - 2;j<colNum + 2;j++) {
                arr[i+2-rowNum][j+2-colNum] = (int) myArray[i][j];
            }
        }
        System.out.println(Arrays.deepToString(arr));
        return arr;
    }
}
