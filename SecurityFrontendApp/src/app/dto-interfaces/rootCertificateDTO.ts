
export interface rootCertificateDTO{
    adminMail:string | null;
    startDate:string;
    endDate:string;
    keyUsages:string[];
    extendedKeyUsages:string[];
}