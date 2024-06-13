import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IServiceMFPAI } from '../service-mfpai.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../service-mfpai.test-samples';

import { ServiceMFPAIService } from './service-mfpai.service';

const requireRestSample: IServiceMFPAI = {
  ...sampleWithRequiredData,
};

describe('ServiceMFPAI Service', () => {
  let service: ServiceMFPAIService;
  let httpMock: HttpTestingController;
  let expectedResult: IServiceMFPAI | IServiceMFPAI[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ServiceMFPAIService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ServiceMFPAI', () => {
      const serviceMFPAI = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(serviceMFPAI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServiceMFPAI', () => {
      const serviceMFPAI = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(serviceMFPAI).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServiceMFPAI', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServiceMFPAI', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServiceMFPAI', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServiceMFPAIToCollectionIfMissing', () => {
      it('should add a ServiceMFPAI to an empty array', () => {
        const serviceMFPAI: IServiceMFPAI = sampleWithRequiredData;
        expectedResult = service.addServiceMFPAIToCollectionIfMissing([], serviceMFPAI);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serviceMFPAI);
      });

      it('should not add a ServiceMFPAI to an array that contains it', () => {
        const serviceMFPAI: IServiceMFPAI = sampleWithRequiredData;
        const serviceMFPAICollection: IServiceMFPAI[] = [
          {
            ...serviceMFPAI,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServiceMFPAIToCollectionIfMissing(serviceMFPAICollection, serviceMFPAI);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServiceMFPAI to an array that doesn't contain it", () => {
        const serviceMFPAI: IServiceMFPAI = sampleWithRequiredData;
        const serviceMFPAICollection: IServiceMFPAI[] = [sampleWithPartialData];
        expectedResult = service.addServiceMFPAIToCollectionIfMissing(serviceMFPAICollection, serviceMFPAI);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serviceMFPAI);
      });

      it('should add only unique ServiceMFPAI to an array', () => {
        const serviceMFPAIArray: IServiceMFPAI[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const serviceMFPAICollection: IServiceMFPAI[] = [sampleWithRequiredData];
        expectedResult = service.addServiceMFPAIToCollectionIfMissing(serviceMFPAICollection, ...serviceMFPAIArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const serviceMFPAI: IServiceMFPAI = sampleWithRequiredData;
        const serviceMFPAI2: IServiceMFPAI = sampleWithPartialData;
        expectedResult = service.addServiceMFPAIToCollectionIfMissing([], serviceMFPAI, serviceMFPAI2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(serviceMFPAI);
        expect(expectedResult).toContain(serviceMFPAI2);
      });

      it('should accept null and undefined values', () => {
        const serviceMFPAI: IServiceMFPAI = sampleWithRequiredData;
        expectedResult = service.addServiceMFPAIToCollectionIfMissing([], null, serviceMFPAI, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(serviceMFPAI);
      });

      it('should return initial array if no ServiceMFPAI is added', () => {
        const serviceMFPAICollection: IServiceMFPAI[] = [sampleWithRequiredData];
        expectedResult = service.addServiceMFPAIToCollectionIfMissing(serviceMFPAICollection, undefined, null);
        expect(expectedResult).toEqual(serviceMFPAICollection);
      });
    });

    describe('compareServiceMFPAI', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServiceMFPAI(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServiceMFPAI(entity1, entity2);
        const compareResult2 = service.compareServiceMFPAI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServiceMFPAI(entity1, entity2);
        const compareResult2 = service.compareServiceMFPAI(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServiceMFPAI(entity1, entity2);
        const compareResult2 = service.compareServiceMFPAI(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
