import { IUrlRepository, UrlData, UrlRequest } from '../../domain/models';

const API_BASE_URL = 'http://localhost:8081/api/v1/urls';

export const ApiUrlRepository: IUrlRepository = {
    shortenUrl: async (request: UrlRequest): Promise<UrlData> => {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(request),
        });

        if (!response.ok) {
            throw new Error('Error al acortar la URL. Verifica los datos.');
        }

        return response.json();
    },

    getAllUrls: async (): Promise<UrlData[]> => {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) {
            throw new Error('Error al obtener las URLs');
        }
        return response.json();
    }
};
