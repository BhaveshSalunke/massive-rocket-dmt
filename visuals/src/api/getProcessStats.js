export function GetProcessStats(processId, onSuccess) {
	const controller = new AbortController()
	fetch(`/api/process/${processId}/stats`, {
		signal: controller.signal
	}).then(onSuccess)
	return () => controller.abort()
}
