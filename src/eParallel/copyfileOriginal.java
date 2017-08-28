package eParallel;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class copyfileOriginal {

	public static void main(String... args) throws IOException {
		Path FROM = Paths.get("C:\\Users\\emormau\\Documents\\empre\\db\\data\\input\\" + args[0]);
		Path TO = Paths.get("C:\\Users\\emormau\\Documents\\empre\\db\\data\\work\\" + args[0]);
		// overwrite existing file, if exists
		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES };
		Files.copy(FROM, TO, options);
		System.out.println("Archivo copiado");
	}
}
