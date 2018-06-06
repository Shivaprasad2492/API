package blog.common;

import java.io.*;
import java.util.*;

public class FileOperations<T> {

    private static final FileOperations fileOperations = new FileOperations();

    private FileOperations() {
    }

    public static FileOperations getInstance() {
        return fileOperations;
    }

    List<T> readAllFiles(String dirPath) {

        synchronized (fileOperations) {

            List<T> arrayList = new ArrayList<T>();

            File file = new File(dirPath);
            File[] files = file.listFiles();

            if (files != null) {

                for (File f : files) {

                    try {
                        FileInputStream fileInputStream = new FileInputStream(f);
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                        T readObject = (T) objectInputStream.readObject();
                        if (readObject != null) {
                            arrayList.add(readObject);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            return arrayList;
        }
    }

    List<T> readRecentFiles(final int numberOfFiles, final String DirLocation) {

        synchronized (fileOperations) {
            Map<Long, File> sortByModificationDate = new TreeMap<Long, File>(Collections.reverseOrder());
            List<T> arrayList = new ArrayList<T>();

            try {
                File file = new File(DirLocation);
                File[] files = file.listFiles();

                if (files != null) {
                    for (File f : files) {
                        sortByModificationDate.put(f.lastModified(), f);
                    }

                    int count = numberOfFiles;
                    for (Long modifiedOn : sortByModificationDate.keySet()) {
                        FileInputStream fileInputStream = new FileInputStream(sortByModificationDate.get(modifiedOn));
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        T readObject = (T) objectInputStream.readObject();
                        if (readObject != null) {
                            arrayList.add(readObject);
                        }
                        count--;
                        if (count <= 0) break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
            return arrayList;
        }
    }

    T readFile(final String filePrefix, final String uniqueId) {

        synchronized (fileOperations) {
            T readObject = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(filePrefix + uniqueId));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                readObject = (T) objectInputStream.readObject();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("Error " + e.getMessage());
            }
            return readObject;
        }
    }

    boolean deleteFile(final String filePrefix, final String uniqueId) {
        synchronized (fileOperations) {
            File file = new File(filePrefix + uniqueId);
            return file.delete();
        }
    }

    public T writeToFile(final String filePrefix, final T object, final String suffix) {

        synchronized (fileOperations) {
            try {

                FileOutputStream fileOutputStream = new FileOutputStream(new File(filePrefix + suffix), true);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                //post.setDate(new Date());
                objectOutputStream.writeObject(object);
                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            return object;
        }
    }


}
