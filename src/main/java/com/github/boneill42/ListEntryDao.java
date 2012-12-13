package com.github.boneill42;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.serializers.AnnotatedCompositeSerializer;
import com.netflix.astyanax.serializers.CompositeRangeBuilder;
import com.netflix.astyanax.serializers.StringSerializer;

public class ListEntryDao extends AstyanaxDao {
    private static final Logger LOG = LoggerFactory.getLogger(ListEntryDao.class);
    public static final String TABLE_NAME = "naughtyornicelist";
    private static AnnotatedCompositeSerializer<ListEntry> ENTITY_SERIALIZER = new AnnotatedCompositeSerializer<ListEntry>(
            ListEntry.class);
    private static ColumnFamily<String, ListEntry> COLUMN_FAMILY = new ColumnFamily<String, ListEntry>(TABLE_NAME,
            StringSerializer.get(), ENTITY_SERIALIZER);

    public ListEntryDao(String host, String keyspace) {
        super(host, keyspace);
    }

    public void write(String rowKey, ListEntry listEntry) throws ConnectionException {
        MutationBatch mutation = this.getKeyspace().prepareMutationBatch();
        mutation.withRow(COLUMN_FAMILY, rowKey).putColumn(listEntry, new byte[0], null);
        mutation.execute();
        LOG.debug("Wrote to list [" + rowKey + "]");
    }

    public ColumnList<ListEntry> read(String rowKey) throws ConnectionException {
        OperationResult<ColumnList<ListEntry>> result = this.getKeyspace().prepareQuery(COLUMN_FAMILY).getKey(rowKey)
                .execute();
        ColumnList<ListEntry> children = result.getResult();
        LOG.debug("Read list [" + rowKey + "]");
        return children;
    }

    public ColumnList<ListEntry> find(String rowKey, String state, String zip)
            throws ConnectionException {
        CompositeRangeBuilder range = ENTITY_SERIALIZER.buildRange();
        // TODO: Track issue here: (https://github.com/Netflix/astyanax/issues/80)
        if (zip == null) {
            range = ENTITY_SERIALIZER.buildRange().withPrefix(state).greaterThanEquals("").lessThanEquals("99999");
        } else {
            range = ENTITY_SERIALIZER.buildRange().withPrefix(state).greaterThanEquals(zip).lessThanEquals(zip);
        }
        OperationResult<ColumnList<ListEntry>> result = this.getKeyspace().prepareQuery(COLUMN_FAMILY).getKey(rowKey)
                .withColumnRange(range).execute();
        ColumnList<ListEntry> children = result.getResult();
        LOG.debug("Found [" + children.size() + "] children in [" + rowKey + "]");
        return children;
    }
}
