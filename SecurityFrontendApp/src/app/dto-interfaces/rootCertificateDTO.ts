
export interface rootCertificateDTO{
    adminMail:string | null;
    startDate:Date;
    endDate:Date;
    keyUsages:string[];
    extendedKeyUsages:string[];
}