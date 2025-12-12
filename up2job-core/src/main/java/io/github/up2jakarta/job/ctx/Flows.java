package io.github.up2jakarta.job.ctx;

import io.github.up2jakarta.job.core.BusinessException;
import io.github.up2jakarta.job.core.JobStatus;
import org.slf4j.Logger;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.UnexpectedJobExecutionException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static io.github.up2jakarta.job.ctx.FlowFormatter.FAILURE;

@SuppressWarnings("unused")
public final class Flows {

    public static final String JOB_COMPLETED = JobStatus.COMPLETED.name();
    public static final String JOB_FAILED = JobStatus.FAILED.name();
    public static final String JOB_REJECTED = JobStatus.REJECTED.name();
    public static final String JOB_CONTINUED = JobStatus.CONTINUED.name();
    private static final String BATCH_FORMAT = "%s(%d/%s)";

    private Flows() {
    }

    public static String stepName(int order, String name) {
        return String.format(BATCH_FORMAT, "Step", order, name);
    }

    public static String flowName(int order, String name) {
        return String.format(BATCH_FORMAT, "Flow", order, name);
    }

    public static long failCount(final StepExecution step) {
        final long failures = Math.max(step.getWriteSkipCount(), step.getRollbackCount());
        return Math.min(failures, processCount(step));
    }

    public static long readCount(final StepExecution step) {
        return step.getReadCount() - step.getFilterCount();
    }

    public static long processCount(final StepExecution step) {
        return readCount(step) - step.getProcessSkipCount();
    }

    public static boolean hasWrites(final StepExecution step) {
        return readCount(step) != step.getProcessSkipCount();
    }

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    public static boolean isStopped(final StepExecution step) {
        return step.getFailureExceptions().stream().anyMatch(Flows::isFatal);
    }

    public static boolean isFatal(final Throwable cause) {
        return (cause instanceof UnexpectedJobExecutionException) || (cause instanceof JobExecutionException);
    }

    public static boolean isRejected(StepExecution step) {
        return JOB_REJECTED.equals(step.getExitStatus().getExitCode());
    }

    public static boolean isContinued(StepExecution step) {
        return JOB_CONTINUED.equals(step.getExitStatus().getExitCode());
    }

    public static boolean isFailed(StepExecution step) {
        return step.getStatus().isUnsuccessful() || !step.getFailureExceptions().isEmpty();
    }

    public static void warning(Logger logger, String ctx, Object uid, Throwable error) {
        if (error instanceof BusinessException) {
            logger.warn("{} execution #[{}] failure cause: {}", ctx, uid, error.getMessage(), error.getCause());
        } else if (isFatal(error)) {
            final Throwable cause = cause(error);
            logger.warn("{} execution #[{}] failure cause: {}", ctx, uid, error.getMessage(), cause);
        } else {
            logger.warn("{} execution #[{}] failure cause: ", ctx, uid, error);
        }
    }

    static String summary(StepExecution s) {
        final String message = s.getExitStatus().getExitDescription();
        final int index = message.indexOf("\n");
        if (index == -1) {
            return s.getStepName() + " - " + message;
        }
        return s.getStepName() + " - " + message.substring(0, index);
    }

    static Collector<CharSequence, ?, String> joining() {
        return Collectors.joining("\n- ", "- ", "");
    }

    static String exitMessage(Throwable error) {
        if (error instanceof BusinessException) {
            if (error.getCause() != null && error != error.getCause()) {
                return error.getMessage() + "\n" + exitMessage(error.getCause());
            }
            return error.getMessage();
        } else if (isFatal(error)) {
            final String cn = error.getClass().getSimpleName();
            return "Unexpected execution exception #" + cn + "[" + error.getMessage() + "]";
        }
        return stackTrace(error);
    }

    static String exitMessage(FlowSource source, List<Throwable> errors) {
        if (errors.isEmpty()) {
            return "";
        } else if (errors.size() == 1) {
            return exitMessage(errors.getFirst());
        }
        final String details = errors.stream().map(Throwable::getMessage).collect(joining());
        return FAILURE.format(source, errors.size()) + ":\n" + details;
    }

    static String stackTrace(Throwable error) {
        final StringWriter writer = new StringWriter();
        final PrintWriter printer = new PrintWriter(writer);
        printer.println(error);
        final StackTraceElement[] traces = error.getStackTrace();
        for (StackTraceElement element : traces) {
            final String cn = element.getClassName();
            if (cn.startsWith("tn.aabbessi.eir.") || cn.startsWith("import org.springframework.batch.")) {
                printer.println("\t" + element);
            }
        }
        final Throwable cause = error.getCause();
        if (cause != null) {
            printer.println(cause);
        }
        return writer.toString();
    }

    static Throwable cause(Throwable error) {
        if (error == null) {
            return null;
        }
        var cause = error;
        while (cause.getCause() != null && cause != cause.getCause()) {
            cause = cause.getCause();
        }
        return cause;
    }

}
