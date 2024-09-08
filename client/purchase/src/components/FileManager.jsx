import React, { useState } from "react";
import FileUpload from "./FileUpload";
import FileList from "./FileList";

const FileManager = () => {
	const [refreshTrigger, setRefreshTrigger] = useState(0);

	const handleFileUploaded = () => {
		setRefreshTrigger((prev) => prev + 1);
	};

	return (
		<>
			<div class="bg-white shadow-md rounded-lg p-6 mb-8">
				<FileUpload onFileUploaded={handleFileUploaded} />
			</div>
			<div class="bg-white shadow-md rounded-lg p-6">
				<FileList refreshTrigger={refreshTrigger} />
			</div>
		</>
	);
};

export default FileManager;
