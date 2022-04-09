
export class CreateCertificateDto{
    constructor(
        public issuerMail:string='',
        public issuerCertificateSerialNumber:string='',
        public issuerCertificateType:string='',
        public subjectMail:string='',
        public startDate:string='',
        public endDate:string='',
        public keyUsages:string[]=[],
        public  extendedKeyUsages:string[]=[],
        public subjectCertificateType:string=''
    ){

    }
}

