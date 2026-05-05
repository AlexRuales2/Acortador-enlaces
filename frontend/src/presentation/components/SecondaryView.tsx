import React, { useEffect, useState } from 'react';
import { ApiUrlRepository } from '../../infrastructure/api/UrlRepository';
import { UrlData } from '../../domain/models';

export default function SecondaryView() {
    const [urls, setUrls] = useState<UrlData[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        loadUrls();
    }, []);

    const loadUrls = async () => {
        try {
            const data = await ApiUrlRepository.getAllUrls();
            setUrls(data);
        } catch (err: any) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <div style={{ textAlign: 'center' }}>Cargando estadísticas...</div>;
    if (error) return <div className="error-text">{error}</div>;

    return (
        <div className="glass-panel" style={{ padding: '2rem 1rem' }}>
            <h3 style={{ marginBottom: '1.5rem', color: 'var(--primary)', paddingLeft: '1rem' }}>Enlaces Registrados</h3>
            
            <div className="data-table-wrapper">
                <table className="data-table">
                    <thead>
                        <tr>
                            <th>Imagen</th>
                            <th>Enlace Corto</th>
                            <th>Enlace Original</th>
                            <th>Descripción</th>
                            <th>Visitas</th>
                        </tr>
                    </thead>
                    <tbody>
                        {urls.length === 0 ? (
                            <tr>
                                <td colSpan={5} style={{ textAlign: 'center' }}>No hay enlaces acortados aún.</td>
                            </tr>
                        ) : (
                            urls.map((url) => (
                                <tr key={url.shortCode}>
                                    <td>
                                        <img src={url.imageUrl} alt="preview" className="table-img" 
                                             onError={(e) => (e.currentTarget.src = 'https://via.placeholder.com/50')} />
                                    </td>
                                    <td>
                                        <a href={`http://localhost:8081/${url.shortCode}`} target="_blank" rel="noopener noreferrer" className="short-link">
                                            /{url.shortCode}
                                        </a>
                                    </td>
                                    <td>
                                        <span className="original-link" title={url.originalUrl}>
                                            {url.originalUrl}
                                        </span>
                                    </td>
                                    <td>
                                        <span style={{ fontSize: '0.9rem', color: 'var(--text-muted)' }}>
                                            {url.description.length > 50 ? url.description.substring(0, 50) + '...' : url.description}
                                        </span>
                                    </td>
                                    <td style={{ fontWeight: 'bold' }}>{url.visits}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
