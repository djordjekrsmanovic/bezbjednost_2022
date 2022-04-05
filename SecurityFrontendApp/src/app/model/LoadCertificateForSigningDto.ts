

export class LoadCertificatesForSigningDto{
    dateFrom:Date;
    dateTo:Date;

    constructor(dateFrom:Date,dateTo:Date){
        this.dateFrom=dateFrom;
        this.dateTo=dateTo;
    }
}