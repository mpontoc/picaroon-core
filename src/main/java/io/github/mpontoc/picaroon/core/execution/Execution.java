package io.github.mpontoc.picaroon.core.execution;

public class Execution {
	
	private static Boolean isFirstRun = null;
	private static Boolean appByRunner = null;
	private static String horaInicial = null;
	private static String horaInicialTotal = null;
	private static String horaFinalTotal = null;
	/**
	 * @return the isFirstRun
	 */
	public static Boolean getIsFirstRun() {
		return isFirstRun;
	}
	/**
	 * @param isFirstRun the isFirstRun to set
	 */
	public static void setIsFirstRun(Boolean isFirstRun) {
		Execution.isFirstRun = isFirstRun;
	}
	/**
	 * @return the appByRunner
	 */
	public static Boolean getAppRunner() {
		return appByRunner;
	}
	/**
	 * @param appByRunner the appByRunner to set
	 */
	public static void setAppRunner(Boolean appByRunner) {
		Execution.appByRunner = appByRunner;
	}
	/**
	 * @return the horaInicial
	 */
	public static String getHoraInicial() {
		return horaInicial;
	}
	/**
	 * @param horaInicial the horaInicial to set
	 */
	public static void setHoraInicial(String horaInicial) {
		Execution.horaInicial = horaInicial;
	}
	/**
	 * @return the horaInicialTotal
	 */
	public static String getHoraInicialTotal() {
		return horaInicialTotal;
	}
	/**
	 * @param horaInicialTotal the horaInicialTotal to set
	 */
	public static void setHoraInicialTotal(String horaInicialTotal) {
		Execution.horaInicialTotal = horaInicialTotal;
	}
	/**
	 * @return the horaFinalTotal
	 */
	public static String getHoraFinalTotal() {
		return horaFinalTotal;
	}
	/**
	 * @param horaFinalTotal the horaFinalTotal to set
	 */
	public static void setHoraFinalTotal(String horaFinalTotal) {
		Execution.horaFinalTotal = horaFinalTotal;
	}
	
}
