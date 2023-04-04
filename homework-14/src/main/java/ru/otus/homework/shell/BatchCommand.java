package ru.otus.homework.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BatchCommand {
    private final Job transferDataJob;

    private final JobLauncher jobLauncher;

    public BatchCommand(Job transferDataJob, JobLauncher jobLauncher) {
        this.transferDataJob = transferDataJob;
        this.jobLauncher = jobLauncher;
    }

    @ShellMethod(value = "startPgToMongoMigration", key = "sm")
    void startPgToMongoMigration() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(transferDataJob, new JobParametersBuilder().toJobParameters());
    }
}
