import React, { useState } from 'react';
import { ApiUrlRepository } from '../../infrastructure/api/UrlRepository';

const isValidUrl = (urlString: string) => {
    try { 
        return Boolean(new URL(urlString)); 
    }
    catch(e){ 
        return false; 
    }
}

export default function MainView() {
    const [originalUrl, setOriginalUrl] = useState('');
    const [imageUrl, setImageUrl] = useState('');
    const [description, setDescription] = useState('');
    
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccessMessage(null);

        // Validaciones Requeridas
        if (!isValidUrl(originalUrl)) {
            setError("La URL original no es válida (requiere http/https).");
            return;
        }
        if (!isValidUrl(imageUrl)) {
            setError("La URL de la imagen no es válida.");
            return;
        }
        
        const wordCount = description.trim().split(/\s+/).length;
        if (wordCount < 5) {
            setError("La descripción debe tener al menos 5 palabras.");
            return;
        }
        if (description.length > 500) {
            setError("La descripción no puede exceder los 500 caracteres.");
            return;
        }

        setLoading(true);
        try {
            const data = await ApiUrlRepository.shortenUrl({
                originalUrl,
                imageUrl,
                description
            });

            const fullShortUrl = `http://localhost:8081/${data.shortCode}`;
            
            // Requerimiento: Reemplazar el campo original inmediatamente por el enlace corto
            setOriginalUrl(fullShortUrl);
            setSuccessMessage("¡Enlace acortado con éxito! El campo de arriba ahora contiene tu enlace corto.");
        } catch (err: any) {
            setError(err.message || "Error desconocido");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="glass-panel">
            <h3 style={{ marginBottom: '1.5rem', color: 'var(--primary)' }}>Acortar Nuevo Enlace</h3>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>URL Original / URL Acortada (Se reemplazará automáticamente)</label>
                    <input 
                        type="url" 
                        className="modern-input"
                        placeholder="https://ejemplo.com"
                        value={originalUrl}
                        onChange={(e) => setOriginalUrl(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>URL de Imagen</label>
                    <input 
                        type="url" 
                        className="modern-input"
                        placeholder="https://ejemplo.com/imagen.jpg"
                        value={imageUrl}
                        onChange={(e) => setImageUrl(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Descripción (Min. 5 palabras, Max. 500 caracteres)</label>
                    <textarea 
                        className="modern-textarea"
                        placeholder="Escribe una breve descripción del enlace..."
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </div>

                {error && <span className="error-text">{error}</span>}
                {successMessage && <span style={{ color: 'var(--primary)', display: 'block', margin: '0.5rem 0' }}>{successMessage}</span>}

                <button type="submit" className="glow-button" disabled={loading} style={{ marginTop: '1rem' }}>
                    {loading ? 'Procesando...' : 'ACORTAR'}
                </button>
            </form>
        </div>
    );
}
