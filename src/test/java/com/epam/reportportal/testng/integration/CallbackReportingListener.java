package com.epam.reportportal.testng.integration;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.Launch;
import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.testng.BaseTestNGListener;
import com.epam.reportportal.testng.TestNGServiceExtension;
import com.epam.ta.reportportal.ws.model.launch.StartLaunchRQ;
import rp.com.google.common.base.Suppliers;

import java.util.Calendar;

/**
 * @author <a href="mailto:ivan_budayeu@epam.com">Ivan Budayeu</a>
 */
public class CallbackReportingListener extends BaseTestNGListener {

	public static final ThreadLocal<ReportPortal> REPORT_PORTAL_THREAD_LOCAL = new ThreadLocal<>();

	public CallbackReportingListener() {
		super(new TestNGServiceExtension(Suppliers.memoize(() -> getLaunch(REPORT_PORTAL_THREAD_LOCAL.get().getParameters())),
				REPORT_PORTAL_THREAD_LOCAL.get()
		));
	}

	public static void initReportPortal(ReportPortal reportPortal) {
		REPORT_PORTAL_THREAD_LOCAL.set(reportPortal);
	}

	public static ReportPortal getReportPortal() {
		return REPORT_PORTAL_THREAD_LOCAL.get();
	}

	private static Launch getLaunch(ListenerParameters parameters) {

		ReportPortal reportPortal = REPORT_PORTAL_THREAD_LOCAL.get();
		StartLaunchRQ rq = new StartLaunchRQ();
		rq.setName(parameters.getLaunchName());
		rq.setStartTime(Calendar.getInstance().getTime());
		rq.setMode(parameters.getLaunchRunningMode());
		rq.setStartTime(Calendar.getInstance().getTime());

		return reportPortal.newLaunch(rq);

	}
}
