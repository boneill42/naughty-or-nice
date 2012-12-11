package com.github.boneill42;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.serializers.StringSerializer;

public class ChildDao extends AstyanaxDao {
    private static final Logger LOG = LoggerFactory.getLogger(ChildDao.class);
    public static final String TABLE_NAME = "children";
    private static final ColumnFamily<String, String> COLUMN_FAMILY = new ColumnFamily<String, String>(TABLE_NAME,
            StringSerializer.get(), StringSerializer.get());

    public ChildDao(String host, String keyspace) {
        super(host, keyspace);
    }

    public void write(String rowKey, Map<String, String> columns) throws ConnectionException {
        MutationBatch mutation = this.getKeyspace().prepareMutationBatch();
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            mutation.withRow(COLUMN_FAMILY, rowKey).putColumn(entry.getKey(), entry.getValue(), null);
        }
        mutation.execute();
        LOG.debug("Wrote child [" + rowKey + "]");
    }

    public ColumnList<String> read(String rowKey) throws ConnectionException {
        OperationResult<ColumnList<String>> result = this.getKeyspace().prepareQuery(COLUMN_FAMILY).getKey(rowKey)
                .execute();
        ColumnList<String> child = result.getResult();
        LOG.debug("Read child [" + rowKey + "]");
        return child;
    }

    public void delete(String rowKey) throws ConnectionException {
        MutationBatch mutation = this.getKeyspace().prepareMutationBatch();
        mutation.withRow(COLUMN_FAMILY, rowKey).delete();
        mutation.execute();
        LOG.debug("Deleted child [" + rowKey + "]");

    }
}
