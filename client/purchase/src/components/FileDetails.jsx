import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CurrencyUtils from '../utils/CurrencyUtils';
import { getToken } from '../utils/TokenUtils';

const FileDetails = ({ correlationId }) => {
    const [fileData, setFileData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = getToken();
        const url = 'http://localhost:8080';
        const fetchFileData = async () => {

            try {
                const response = await axios.get(url + `/api/v1/purchase/${correlationId}`, {
                    headers: {
                        Authorization: 'Bearer ' + token,
                    }
                });
                setFileData(response.data);
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

        fetchFileData();
    }, [correlationId]);

    const hasData = fileData && fileData.length > 0;

    if (loading) {
        return <p className="text-lg font-semibold text-gray-700">Carregando dados...</p>;
    }

    if (error) {
        return <p className="text-lg font-semibold text-gray-700">{error}</p>;
    }

    return (
        <div className="overflow-x-auto">

            <p className="text-lg font-semibold text-gray-700 mb-4">
                Identificador do arquivo: <span className="text-green-600">{correlationId}</span>
            </p>

            {hasData ? (
                <table className="min-w-full bg-white">
                    <thead className="bg-gray-100">
                        <tr>
                            <th className="py-2 px-4 text-left">Nome do comprador</th>
                            <th className="py-2 px-4 text-left">Descrição do item</th>
                            <th className="py-2 px-4 text-left">Valor do item</th>
                            <th className="py-2 px-4 text-left">Quantidade de compras</th>
                            <th className="py-2 px-4 text-left">Endereço do vendedor</th>
                            <th className="py-2 px-4 text-left">Nome do vendedor</th>
                        </tr>
                    </thead>
                    <tbody>
                        {fileData.map((item) => (
                            <tr className="border-b">
                                <td className="py-2 px-4">{item.purchaserName}</td>
                                <td className="py-2 px-4">{item.itemDescription}</td>
                                <td className="py-2 px-4">{CurrencyUtils.formatCurrency(item.itemPrice)}</td>
                                <td className="py-2 px-4">{item.purchaseCount}</td>
                                <td className="py-2 px-4">{item.merchantAddress}</td>
                                <td className="py-2 px-4">{item.merchantName}</td>
                            </tr>
                        ))}
                    </tbody>

                </table>

            ) : (
                <p className="text-lg font-semibold text-gray-700">
                    Não há registros disponíveis.
                </p>
            )}
        </div>
    );
};
export default FileDetails;