package Tests.TestData;

    public class RecordingOutputStream extends java.io.OutputStream {
        private final java.io.OutputStream out;
        private byte[] buffer = new byte[1 << 10];
        private int size = -1;

        public RecordingOutputStream(java.io.OutputStream out) {
            this.out = out;
        }

        @Override
        public void write(int b) throws java.io.IOException {
            if (size >= 0) {
                if (size == buffer.length) {
                    buffer = java.util.Arrays.copyOf(buffer, size * 2);
                }
                buffer[size++] = (byte) b;
            } else {
                out.write(b);
            }
        }

        public void start() {
            size = 0;
        }

        public String stop() {
            String retval = new String(buffer, 0, size);
            size = -1;
            return retval;
        }
    }