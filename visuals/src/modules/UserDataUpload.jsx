import {useState} from "react";
import CsvFileSelector from "../components/CsvFileSelector";
import CsvFileUploader from "../components/CsvFileUploader";
import ProcessStatChecker from "../components/ProcessStatChecker";

export function UserDataUpload() {
	const showFileUploader = useState(false)
	const [selectedFile, setSelectedFile] = useState()
	const [processId, setProcessId] = useState()

	function onFileUploadSuccess(processId) {
		setSelectedFile(undefined)
		setProcessId(processId)
	}

	return (
		<>
			<button
				className={`bg-blue-500 rounded-lg px-8 py-4 text-white font-bold cursor-pointer ${selectedFile && "bg-blue-300 cursor-progress"}`}
				onClick={() => showFileUploader[1](true)}
				disabled={selectedFile}
			>
				Upload CSV
			</button>
			<CsvFileSelector displayState={showFileUploader} submitFileSelector={setSelectedFile}/>
			{selectedFile && <CsvFileUploader file={selectedFile} onSuccess={onFileUploadSuccess}/>}
			{processId && <ProcessStatChecker processId={processId} onProcessComplete={() => {
				setProcessId(undefined)
				console.log("processing finished")
			}}/>}
		</>
	)
}
