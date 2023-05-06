import {useState} from "react"

export default function CsvFileSelector({displayState, submitFileSelector}) {
	const [selectedFile, setSelectedFile] = useState()

	function submitSelection() {
		if (selectedFile) submitFileSelector(selectedFile)
		closeDialog()
	}

	function closeDialog() {
		setSelectedFile(null)
		displayState[1](false)
	}

	function handleDragOver(e) {
		e.preventDefault()
		e.stopPropagation()
	}

	function handleDrop(e) {
		e.preventDefault()
		e.stopPropagation()

		const files = [...e.dataTransfer.files]

		if (files.length !== 1) {
			console.log("Please select only one file at a time.")
			return
		}

		if (!files[0].name.toLowerCase().endsWith("csv")) {
			console.log("Please select a csv file.")
			return
		}

		if (files && files.length) setSelectedFile(files[0])
	}

	if (!displayState[0]) return null

	return (
		<div className="fixed inset-0 flex justify-center items-center bg-stone-200 bg-opacity-50"
				 onClick={closeDialog}>
			<div className="rounded-lg bg-white p-6" onClick={(e) => e.stopPropagation()}>
				<span className="flex justify-end text-stone-400"><button
					onClick={closeDialog}>X</button></span>
				<div className="mt-6 w-[20em] h-[12em] border rounded-xl flex justify-center items-center"
						 onDragOver={handleDragOver}
						 onDrop={handleDrop}
				>
					<p className="text-stone-600">Drag and drop file here</p>
				</div>
				<div className="my-4 flex justify-center items-center space-x-4">
					<span className="flex-1 border-b h-1"></span>
					<p className="text-sm text-stone-400">OR</p>
					<span className="flex-1 border-b h-1"></span>
				</div>
				<div className="w-full mt-3 flex justify-center">
					<label htmlFor="file-input"
								 className="w-full flex-1 border-blue-500 border-2 px-6 py-3 text-blue-500 rounded-lg text-center font-semibold cursor-pointer">
						Select file
					</label>
					<input type="file" id="file-input" className="hidden" accept=".csv" multiple={false} onChange={e => setSelectedFile(e.target.files[0])}/>
				</div>
				<div className="my-6 w-full text-ellipsis">{selectedFile ? <p>Selected file: {selectedFile.name}</p> : <p>No file selected</p>}</div>
				<button className="w-full flex-1 bg-blue-500 px-6 py-3 text-white rounded-lg text-center font-semibold cursor-pointer" onClick={submitSelection}>Upload</button>
			</div>
		</div>
	)
}
