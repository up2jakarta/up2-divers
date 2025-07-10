package io.github.up2jakarta.job.ctx;

public class FlowFormatter {

    // Instances
    public static final FlowFormatter REJECTION = new FlowFormatter("rejection");
    public static final FlowFormatter WARNING = new FlowFormatter("warning");
    public static final FlowFormatter FAILURE = new FlowFormatter("failure");
    // Format
    private static final String FORMAT = "%s exits with (%d) %s(s)";

    private final String severity;

    private FlowFormatter(String severity) {
        this.severity = severity;
    }

    public String format(FlowSource source, long count) {
        if (source == null) {
            source = FlowSource.JOB;
        }
        return String.format(FORMAT, source.getName(), count, severity);
    }

}
