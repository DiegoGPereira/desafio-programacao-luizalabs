import React, { useEffect, useState } from 'react';
import axios from 'axios';
import CurrencyUtils from '../utils/CurrencyUtils';

const DataTable = () => {
  const [files, setFiles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const handleFileChange = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/v1/purchase/uploaded-files`);
      setFiles(response.data);
      setLoading(false);
    } catch (error) {
      setError('Erro ao buscar os dados');
      setLoading(false);
    }
  };

  useEffect(() => {
    handleFileChange();
  }, []);

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
                  <td className="py-2 px-4">{item.inclusionDate}</td>
                  <td className="py-2 px-4"><span className="text-green-600">{CurrencyUtils.formatCurrency(item.grossByCorrelationId)}</span></td>
                  <td className="py-2 px-4">
                    <a
                      href={`/file-details/${item.correlationId}`}
                      className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded inline-flex items-center"
                    >
                      Registros do arquivo
                    </a >
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <p className="text-lg font-semibold text-gray-700 mt-4">
            Receita bruta: <span className="text-green-600">{CurrencyUtils.formatCurrency(files[0].totalGross)}</span>
          </p>
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
