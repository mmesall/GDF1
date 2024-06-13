import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPriseEnCharge } from '../prise-en-charge.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prise-en-charge.test-samples';

import { PriseEnChargeService } from './prise-en-charge.service';

const requireRestSample: IPriseEnCharge = {
  ...sampleWithRequiredData,
};

describe('PriseEnCharge Service', () => {
  let service: PriseEnChargeService;
  let httpMock: HttpTestingController;
  let expectedResult: IPriseEnCharge | IPriseEnCharge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PriseEnChargeService);
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

    it('should create a PriseEnCharge', () => {
      const priseEnCharge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(priseEnCharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PriseEnCharge', () => {
      const priseEnCharge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(priseEnCharge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PriseEnCharge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PriseEnCharge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PriseEnCharge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPriseEnChargeToCollectionIfMissing', () => {
      it('should add a PriseEnCharge to an empty array', () => {
        const priseEnCharge: IPriseEnCharge = sampleWithRequiredData;
        expectedResult = service.addPriseEnChargeToCollectionIfMissing([], priseEnCharge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(priseEnCharge);
      });

      it('should not add a PriseEnCharge to an array that contains it', () => {
        const priseEnCharge: IPriseEnCharge = sampleWithRequiredData;
        const priseEnChargeCollection: IPriseEnCharge[] = [
          {
            ...priseEnCharge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, priseEnCharge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PriseEnCharge to an array that doesn't contain it", () => {
        const priseEnCharge: IPriseEnCharge = sampleWithRequiredData;
        const priseEnChargeCollection: IPriseEnCharge[] = [sampleWithPartialData];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, priseEnCharge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(priseEnCharge);
      });

      it('should add only unique PriseEnCharge to an array', () => {
        const priseEnChargeArray: IPriseEnCharge[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const priseEnChargeCollection: IPriseEnCharge[] = [sampleWithRequiredData];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, ...priseEnChargeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const priseEnCharge: IPriseEnCharge = sampleWithRequiredData;
        const priseEnCharge2: IPriseEnCharge = sampleWithPartialData;
        expectedResult = service.addPriseEnChargeToCollectionIfMissing([], priseEnCharge, priseEnCharge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(priseEnCharge);
        expect(expectedResult).toContain(priseEnCharge2);
      });

      it('should accept null and undefined values', () => {
        const priseEnCharge: IPriseEnCharge = sampleWithRequiredData;
        expectedResult = service.addPriseEnChargeToCollectionIfMissing([], null, priseEnCharge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(priseEnCharge);
      });

      it('should return initial array if no PriseEnCharge is added', () => {
        const priseEnChargeCollection: IPriseEnCharge[] = [sampleWithRequiredData];
        expectedResult = service.addPriseEnChargeToCollectionIfMissing(priseEnChargeCollection, undefined, null);
        expect(expectedResult).toEqual(priseEnChargeCollection);
      });
    });

    describe('comparePriseEnCharge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePriseEnCharge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePriseEnCharge(entity1, entity2);
        const compareResult2 = service.comparePriseEnCharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePriseEnCharge(entity1, entity2);
        const compareResult2 = service.comparePriseEnCharge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePriseEnCharge(entity1, entity2);
        const compareResult2 = service.comparePriseEnCharge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
