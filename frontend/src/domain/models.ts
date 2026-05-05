export interface UrlData {
    shortCode: string;
    originalUrl: string;
    imageUrl: string;
    description: string;
}

export interface UrlRequest {
    originalUrl: string;
    imageUrl: string;
    description: string;
}

export interface IUrlRepository {
    shortenUrl(request: UrlRequest): Promise<UrlData>;
    getAllUrls(): Promise<UrlData[]>;
}
