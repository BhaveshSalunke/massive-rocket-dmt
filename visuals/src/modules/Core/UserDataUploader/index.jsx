import {useState} from "react"
import CsvFileSelector from "../../../components/CsvFileSelector"
import CsvFileUploader from "./CsvFileUploader"

export default function UserDataUploader({setProcessId}) {
	const showFileUploader = useState(false)
	const [selectedFile, setSelectedFile] = useState()

	return (
		<div>
			<button
				className={`bg-blue-500 rounded-lg px-8 py-4 text-white font-bold cursor-pointer ${selectedFile && "bg-blue-300 cursor-progress"}`}
				onClick={() => showFileUploader[1](true)}
				disabled={selectedFile}
			>
				Upload CSV
			</button>
			<CsvFileSelector displayState={showFileUploader} submitFileSelector={setSelectedFile}/>
			{selectedFile && <CsvFileUploader file={selectedFile} onSuccess={(processId) => {
				setProcessId(processId)
				setSelectedFile(undefined)
			}}/>}
		</div>
	)
}
