package bb.mom.tete.components.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xtdb.api.IXtdb;
import xtdb.api.TransactionInstant;
import xtdb.api.XtdbDocument;
import xtdb.api.tx.Transaction;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class XTDB {
    @Bean
    @Scope("singleton")
    @Qualifier("InMemory")
    public static IXtdb start() {
        return start(Map.of());
    }

    public static IXtdb start(final Map<?, ?> confMap) {
        return IXtdb.startNode(confMap);
    }

    public static TransactionInstant ingest(final IXtdb xtdb, final Collection<XtdbDocument> docs) {
        var tx = docs.stream()
                .reduce(Transaction.builder(), Transaction.Builder::put, (a, b) -> a)
                .build();
        return xtdb.submitTx(tx);
    }

    public static Collection<List<?>> query(final TransactionInstant txInstant, final IXtdb xtdb, String q) throws IOException {
        try (var db = xtdb.db(txInstant)) {
            return db.query(q);
        }
    }
}
