export function UploadUserDataFile(file, onSuccess) {
	const formData = new FormData()
	const controller = new AbortController()
	formData.append("file", file)
	fetch("/api/users/dataset", {
		method: "post",
		body: formData,
		signal: controller.signal
	}).then(() => onSuccess())
	return () => controller.abort()
}
