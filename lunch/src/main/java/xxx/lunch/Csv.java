package xxx.lunch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Csv {
    public static final CsvMapper CSV = new CsvMapper();

    static public <T> List<T> parse(Reader csv, Class<T> clz) throws CsvException {
        try {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            MappingIterator<T> it = CSV.readerFor(clz).with(schema).readValues(csv);
            ArrayList<T> ret = new ArrayList<T>();
            while (it.hasNext()) {
                ret.add(it.next());
            }
            return ret;
        } catch (IOException e) {
            throw new CsvException(e);
        }
    }

    static public <T> void writeTo(List<T> lst, Class<T> clz, OutputStream out) throws CsvException {
        try {
            CsvSchema schema = CSV.schemaFor(clz).withHeader();
            OutputStreamWriter writer = new OutputStreamWriter(out, Charset.forName("UTF-8"));
            CSV.writer(schema).writeValues(writer).writeAll(lst);
            writer.close();
        } catch (JsonProcessingException e) {
            throw new CsvException(e);
        } catch (IOException e) {
            throw new CsvException(e);
        }
    }
}
