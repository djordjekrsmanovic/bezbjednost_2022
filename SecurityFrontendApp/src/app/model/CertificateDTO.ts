import { InformationData } from "./InformationData";

export class CertificateDTO {

    public subjectData: InformationData;
    public issuerData: InformationData;
    public serialNumber: string;
    public startDate: Date;
    public endDate: Date;
    public keyUsages: string[];
    public extendedKey: string[];
    public extendedKeyUsages: string[];
    public certificateType: string;
    public revoked: boolean;

    constructor(subjectData: InformationData,
                issuerData: InformationData,
                serialNumber: string,
                startDate: Date,
                endDate: Date,
                keyUsages: string[],
                extendedKey: string[],
                extendedKeyUsages: string[],
                certificateType: string,
                revoked: boolean)
    {
        this.subjectData = subjectData;
        this.issuerData = issuerData;
        this.serialNumber = serialNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyUsages = keyUsages;
        this.extendedKey = extendedKey;
        this.extendedKeyUsages = extendedKeyUsages;
        this.certificateType = certificateType;
        this.revoked = revoked;
        
    }


   
}