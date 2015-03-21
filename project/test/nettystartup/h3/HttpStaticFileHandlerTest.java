package nettystartup.h3;

import io.netty.channel.FileRegion;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HttpStaticFileHandlerTest {
    @Test
    public void index() throws Exception {
        String index = HttpStaticServer.index;
        EmbeddedChannel ch = new EmbeddedChannel(new HttpStaticFileHandler("/", index));
        ch.writeInbound(new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/"));
        ch.writeInbound(new DefaultLastHttpContent());
        assertThat(ch.readOutbound(), instanceOf(HttpResponse.class));
        FileRegion content = (FileRegion) ch.readOutbound();
        assertTrue(content.count() > 0);
        assertThat(ch.readInbound(), instanceOf(LastHttpContent.class));
    }
}
