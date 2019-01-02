/**
 * 
 */
package com.rkvs.stBlockCh.utilities;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.rkvs.stBlockCh.model.UserDetails;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author rsaladi
 *
 */
public interface StorageService {
    void init();
    Stream<Path> loadAll();
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
	String store(MultipartFile file, boolean global_flag, UserDetails user);
}
