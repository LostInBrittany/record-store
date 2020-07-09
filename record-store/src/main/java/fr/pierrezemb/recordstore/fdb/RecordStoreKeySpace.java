package fr.pierrezemb.recordstore.fdb;

import com.apple.foundationdb.record.provider.foundationdb.keyspace.DirectoryLayerDirectory;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpace;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpaceDirectory;
import com.apple.foundationdb.record.provider.foundationdb.keyspace.KeySpacePath;

public class RecordStoreKeySpace {
  public static final String APPLICATION_NAME = "record-store";
  public static final KeySpace RS_KEY_SPACE =
    new KeySpace(
      new DirectoryLayerDirectory("application")
        .addSubdirectory(new KeySpaceDirectory("tenant", KeySpaceDirectory.KeyType.STRING)
          .addSubdirectory(new KeySpaceDirectory("recordSpace", KeySpaceDirectory.KeyType.STRING)
            .addSubdirectory(new KeySpaceDirectory("metadata", KeySpaceDirectory.KeyType.STRING, "m"))
            .addSubdirectory(new KeySpaceDirectory("data", KeySpaceDirectory.KeyType.STRING, "d"))
          )));

  public static KeySpacePath getMetaDataKeySpacePath(String tenant, String recordSpace) {
    return getKeySpacePath(tenant, recordSpace, "metadata");
  }

  public static KeySpacePath getApplicationKeySpacePath(String tenant) {
    return RS_KEY_SPACE
      .path("application", APPLICATION_NAME)
      .add("tenant", tenant);
  }

  public static KeySpacePath getDataKeySpacePath(String tenant, String recordSpace) {
    return getKeySpacePath(tenant, recordSpace, "data");
  }

  private static KeySpacePath getKeySpacePath(String tenant, String env, String subDirectory) {
    return RS_KEY_SPACE
      .path("application", APPLICATION_NAME)
      .add("tenant", tenant)
      .add("recordSpace", env)
      .add(subDirectory);
  }
}
