package com.dvlcube.cuber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.dvlcube.cuber.utils.FileUtils;

/**
 * 
 * @since 25/06/2013
 * @author wonka
 */
public class CubeFile {
	public File o;

	/**
	 * @param path
	 * @since 25/06/2013
	 * @author wonka
	 */
	public CubeFile(String path, boolean create) {
		o = new File(path);
		if (!o.isFile()) {
			if (create)
				try {
					if (path.endsWith("/"))
						o.mkdir();
					else
						o.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				throw new IllegalArgumentException("not a file: '" + path + "'");
		}
	}

	public CubeFile(String path) {
		this(path, true);
	}

	/**
	 * @param path
	 * @since 04/07/2013
	 * @author wonka
	 */
	public CubeFile(CubeString path) {
		this(path.o, true);
	}

	public boolean isDir() {
		return o.isDirectory();
	}

	public String firstLine() {
		return FileUtils.firstLine(o);
	}

	public List<String> head(int lines) {
		return FileUtils.head(o, lines);
	}

	/**
	 * Appends a line to the file, line ends with line break.
	 * 
	 * @param line
	 *            line to append.
	 * @return this.
	 * @since 07/07/2013
	 * @author wonka
	 */
	public CubeFile append(String line) {
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(o, true)))) {
			writer.append(line + "/n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Overwrites a file.
	 * 
	 * @param line
	 *            line to write.
	 * @return this.
	 * @since 08/07/2013
	 * @author wonka
	 */
	public CubeFile write(String line) {
		try (PrintWriter writer = new PrintWriter(o)) {
			writer.write(line);
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * @param extension
	 *            the desired extension.
	 * @return true if the file has the passed extension.
	 * @since 07/07/2013
	 * @author wonka
	 */
	public boolean is(String extension) {
		if (o == null)
			throw new IllegalStateException("file was not initialized");

		if (o.getPath().endsWith(extension))
			return true;

		return false;
	}

	/**
	 * @param dir
	 *            directory to create.
	 * @return this.
	 * @since 08/07/2013
	 * @author wonka
	 */
	public CubeFile mkdir(String dir) {
		if (o == null)
			throw new IllegalStateException("file was not initialized");

		if (!o.isDirectory())
			throw new IllegalStateException("this is not a dir");

		File newDir = new File(o.getPath() + "/" + dir);
		o = newDir;
		newDir.mkdir();
		return this;
	}

	/**
	 * @param name
	 *            file to create.
	 * @return this.
	 * @since 08/07/2013
	 * @author wonka
	 */
	public CubeFile newFile(String name) {
		if (o == null)
			throw new IllegalStateException("file was not initialized");

		if (!o.isDirectory())
			throw new IllegalStateException("this is not a dir");

		try {
			File newFile = new File(o.getPath() + "/" + name);
			o = newFile;
			newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * @since 09/07/2013
	 * @author wonka
	 */
	public void rm() {
		o.delete();
	}

	/**
	 * @return this file name, without the extension.
	 * @since 09/07/2013
	 * @author wonka
	 */
	public String nameSansExtension() {
		return FileUtils.removeExtension(o.getPath());
	}

	/**
	 * Moves this file to another location.
	 * 
	 * @param dir
	 *            destination.
	 * @return this.
	 * @since 09/07/2013
	 * @author wonka
	 */
	public CubeFile mv(String dir) {
		o = FileUtils.moveFileToDirectory(o, new File(dir));
		return this;
	}

	@Override
	public String toString() {
		return o.toString();
	}
}
