import { InformationData } from "./InformationData";

export class CertificateDTO {

    subjectData: InformationData;
    issuerData: InformationData;
    serialNumber: string;
    startDate: Date;
    endDate: Date;
    keyUsages: string[];
    extendedKey: string[];
    extendedKeyUsages: string[];
    certificateType: string;
    isRevoked: boolean;

    constructor(subjectData: InformationData,
                issuerData: InformationData,
                serialNumber: string,
                startDate: Date,
                endDate: Date,
                keyUsages: string[],
                extendedKey: string[],
                extendedKeyUsages: string[],
                certificateType: string,
                isRevoked: boolean)
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
        this.isRevoked = isRevoked;
        
    }


   
}