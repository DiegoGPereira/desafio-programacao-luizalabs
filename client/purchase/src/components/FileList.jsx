import React, { useEffect, useState } from 'react';
import axios from 'axios';
import CurrencyUtils from '../utils/CurrencyUtils';
import DateUtils from '../utils/DateUtils';
import { getToken } from '../utils/TokenUtils';

const DataTable = ({ refreshTrigger }) => {
  const [files, setFiles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);


  const handleFileChange = async () => {
    const token = getToken();
    const apiUrl = import.meta.env.PUBLIC_API_URL || 'http://localhost:8080';

    try {
      const response = await axios.get(apiUrl + '/api/v1/purchase/uploaded-files', {
        headers: {
          Authorization: 'Bearer ' + token,
        },
      });

      setFiles(response.data);
      setLoading(false);
    } catch (error) {
      setLoading(false);
      if (error.response) {
        setError(`Erro ao buscar os dados: ${error.response.data.message || error.response.data}`);
      } else if (error.request) {
        setError('Erro ao buscar os dados: Sem resposta do servidor, ' + error.message);
      } else {
        setError('Erro ao buscar os dados: ' + error.message);
      }
    }
  };

  useEffect(() => {
    handleFileChange();
  }, [refreshTrigger]);

  const hasFiles = files && files.length > 0;

  if (loading) {
    return <p className="text-lg font-semibold text-gray-700">Carregando dados...</p>;
  }

  if (error) {
    return <p className="text-lg font-semibold text-gray-700">{error}</p>;
  }

  return (
    <div className="overflow-x-auto">
      {hasFiles ? (
        <div>
          <p className="text-lg font-semibold text-gray-700 mb-4">
            Receita bruta: <span className="text-green-600">{CurrencyUtils.formatCurrency(files[0].totalGross)}</span>
          </p>
          <table className="min-w-full bg-white">
            <thead className="bg-gray-100">
              <tr>
                <th className="py-2 px-4 text-left">Identificador do arquivo</th>
                <th className="py-2 px-4 text-left">Data de inclusão</th>
                <th className="py-2 px-4 text-left">Receita bruta do arquivo</th>
                <th className="py-2 px-4 text-left">Ações</th>
              </tr>
            </thead>
            <tbody>
              {files.map((item) => (
                <tr className="border-b">
                  <td className="py-2 px-4">{item.correlationId}</td>
                  <td className="py-2 px-4">{DateUtils.formatDate(item.inclusionDate)}</td>
                  <td className="py-2 px-4"><span className="text-green-600">{CurrencyUtils.formatCurrency(item.grossByCorrelationId)}</span></td>
                  <td className="py-2 px-4">
                    <a
                      href={`/file-details/${item.correlationId}`}
                      className="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded inline-flex items-center"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                        <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                      </svg>
                      <span className="ml-2">Detalhes</span>
                    </a >
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <p className="text-lg font-semibold text-gray-700">
          Não há arquivos disponíveis.
        </p>
      )}
    </div>
  );
};

export default DataTable;
