package com.thinking.my.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAround {

	public static void main(String ...args) throws IOException{

        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("---");

		String oneLine = processFile2("/Users/liyong/Desktop/data.txt",(BufferedReader b) -> b.readLine());
		System.out.println(oneLine);
		String oneLine2 = processFile((BufferedReader b) -> b.readLine());
		System.out.println(oneLine2);

		String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
		System.out.println(twoLines);

	}

    public static String processFileLimited() throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("/Users/liyong/Desktop/data.txt"))) {
            return br.readLine();
        }
    }


	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/liyong/Desktop/data.txt"))){
			return p.process(br);
		}

	}
	public static String processFile2(String path ,BufferedReaderProcessor p) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			return p.process(br);
		}

	}

	public interface BufferedReaderProcessor{
		public String process(BufferedReader b) throws IOException;

	}
	public interface BufferedReaderPathProcessor{
		public String process(BufferedReader b) throws IOException;

	}
}