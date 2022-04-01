
export interface rootCertificateDTO{
    email:string | null;
    startDate:Date;
    endDate:Date;
    keyUsages:string[];
    extendedKeyUsages:string[];
}