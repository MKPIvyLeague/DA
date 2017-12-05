package com.src.wavefront.obj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveFrontObjectLoader {

	private static final String path = System.getProperty("user.dir");

	public static void main(String[] args) throws IOException {

		// The name of the file to open.
		String fileName = "/Users/meethunpanda/Downloads/dodecahedron.obj";

		// This will reference one line at a time
		String line = null;
		BufferedReader bufferedReader = null;
		List<Coordinate> vertexList = new ArrayList<Coordinate>();
		List<Coordinate> faceList = new ArrayList<Coordinate>();

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			bufferedReader = new BufferedReader(fileReader);

			while (bufferedReader.ready()) {
				line = bufferedReader.readLine();
				if (line.startsWith("v")) {
					Coordinate c = addVertices(line);
					vertexList.add(c);
				}
				if (line.startsWith("f")) {
					Coordinate f = addVertices(line);
					faceList.add(f);
				}
			}

			for (Coordinate c : vertexList) {
				 System.out.println("X -> " + c.getX() + ",Y -> " + c.getY() +
				 ",Z -> " + c.getZ());
			}

			createObjOutFile(vertexList, faceList);

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		} finally {
			bufferedReader.close();
		}

	}
	
	/**
	 * This method creates an output obj file.
	 * @param vertexList
	 * @param faceList
	 * @throws IOException
	 */
	private static void createObjOutFile(List<Coordinate> vertexList, List<Coordinate> faceList) throws IOException {
		BufferedWriter bufferedWriter = null;
		try {
			File myFile = new File(path + "/src/" + "output.obj");
			Writer writer = new FileWriter(myFile);
			bufferedWriter = new BufferedWriter(writer);

			bufferedWriter.write("g Object001");
			bufferedWriter.newLine();
			for (Coordinate c : vertexList) {
				String line = "v  " + (double) c.getX()+1d + "  " + c.getY() + "  " + c.getZ();
				bufferedWriter.write(line);
				bufferedWriter.newLine();
			}
			bufferedWriter.newLine();

			for (Coordinate c : faceList) {
				String line = "f  " + (int) c.getX() + "  " + (int) c.getY() + "  " + (int) c.getZ();
				bufferedWriter.write(line);
				bufferedWriter.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bufferedWriter.close();
		}

	}

	/**
	 * This method splits the line in order to store x,y,z coordinates.
	 * @param line
	 * @return
	 */
	private static Coordinate addVertices(String line) {

		String[] str = line.split("  ");
		double x = Double.parseDouble(str[1]) * (double)2;
		double y = Double.parseDouble(str[2]);
		double z = Double.parseDouble(str[3]);

		return new Coordinate(x, y, z);
	}

}
