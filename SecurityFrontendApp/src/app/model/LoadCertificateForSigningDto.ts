

export class LoadCertificatesForSigningDto{
    dateFrom:string;
    dateTo:string;

    constructor(dateFrom:string,dateTo:string){
        this.dateFrom=dateFrom;
        this.dateTo=dateTo;
    }
}