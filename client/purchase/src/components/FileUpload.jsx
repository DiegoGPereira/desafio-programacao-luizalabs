import React, { useState } from 'react';
import axios from 'axios';
import { getToken } from '../utils/TokenUtils';

const FileUpload = ({ onFileUploaded }) => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [uploadStatus, setUploadStatus] = useState('');

    const handleFileChange = (event) => {
        setSelectedFile(event.target.files[0]);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('file', selectedFile);
        const token = getToken();
        const apiUrl = import.meta.env.PUBLIC_API_URL || 'http://localhost:8080';

        try {
            const response = await axios.post(apiUrl + '/api/v1/purchase/file', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    Authorization: 'Bearer ' + token,
                },
            });

            setUploadStatus('Arquivo carregado com sucesso!');
            onFileUploaded();
        } catch (error) {
            if (error.response.data) {
                setUploadStatus(`Falha ao enviar o arquivo: ${error.response.data.message || error.response.data}`);
            } else if (error.request) {
                setUploadStatus('Falha ao enviar o arquivo: Sem resposta do servidor, ' + error.message);
            } else {
                setUploadStatus('Falha ao enviar o arquivo: ' + error.message);
            }
        }
    };

    return (
        <div className="max-w-md mx-auto mt-8 p-6 bg-white rounded-lg shadow-md">
            <form onSubmit={handleSubmit} className="space-y-4">
                <div className="flex items-center justify-center w-full">
                    <label htmlFor="file-upload" className="flex flex-col items-center justify-center w-full h-32 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100">
                        <div className="flex flex-col items-center justify-center pt-5 pb-6">
                            <svg className="w-8 h-8 mb-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
                            </svg>
                            {selectedFile ? (
                                <p className="mb-2 text-sm text-gray-500">{selectedFile.name}</p>
                            ) : (
                                <p className="mb-2 text-sm text-gray-500"><span className="font-semibold">Clique para enviar</span> ou arraste e solte o arquivo</p>
                            )}
                        </div>
                        <input id="file-upload" type="file" className="hidden" onChange={handleFileChange} />
                    </label>
                </div>
                <button type="submit" className="w-full px-4 py-2 text-white bg-blue-500 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
                    Enviar
                </button>
            </form>
            {uploadStatus && <p className="mt-4 text-sm text-gray-600 font-bold">{uploadStatus}</p>}
        </div>
    );
};

export default FileUpload;