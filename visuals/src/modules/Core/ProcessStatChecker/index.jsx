import {useEffect, useState} from "react"
import {GetProcessStats} from "../../../api/getProcessStats"

export default function ProcessStatChecker({processId, onProcessComplete}) {
	const [isProcessing, setIsProcessing] = useState(true)

	function checkProcessStatus(interval) {
		GetProcessStats(processId, async (response) => {
			const processing = (await response.json())["processStatus"] === "PROCESSING"
			setIsProcessing(processing)
			if (!processing) {
				onProcessComplete()
				clearInterval(interval)
			}
		})
	}

	useEffect(() => {
		const interval = setInterval(() => checkProcessStatus(interval), 2500)
		return () => clearInterval(interval)
	}, [])

	if (!isProcessing) return null

	return (
		<div className="fixed right-4 md:right-16 bottom-8">
			<div className="bg-white border rounded-lg px-6 py-3 flex items-center space-x-4">
				<div>
					<svg className="animate-spin -ml-1 mr-1 h-8 w-8 text-green-500" xmlns="http://www.w3.org/2000/svg" fill="none"
							 viewBox="0 0 24 24">
						<circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
						<path className="opacity-75" fill="currentColor"
									d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
					</svg>
				</div>
				<div>
					<p className="">Processing File</p>
				</div>
			</div>
		</div>
	)
}
