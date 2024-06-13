import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICandidatureE } from '../candidature-e.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../candidature-e.test-samples';

import { CandidatureEService, RestCandidatureE } from './candidature-e.service';

const requireRestSample: RestCandidatureE = {
  ...sampleWithRequiredData,
  dateDebutOffre: sampleWithRequiredData.dateDebutOffre?.format(DATE_FORMAT),
  dateFinOffre: sampleWithRequiredData.dateFinOffre?.format(DATE_FORMAT),
  dateDepot: sampleWithRequiredData.dateDepot?.format(DATE_FORMAT),
};

describe('CandidatureE Service', () => {
  let service: CandidatureEService;
  let httpMock: HttpTestingController;
  let expectedResult: ICandidatureE | ICandidatureE[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CandidatureEService);
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

    it('should create a CandidatureE', () => {
      const candidatureE = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(candidatureE).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CandidatureE', () => {
      const candidatureE = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(candidatureE).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CandidatureE', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CandidatureE', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CandidatureE', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCandidatureEToCollectionIfMissing', () => {
      it('should add a CandidatureE to an empty array', () => {
        const candidatureE: ICandidatureE = sampleWithRequiredData;
        expectedResult = service.addCandidatureEToCollectionIfMissing([], candidatureE);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(candidatureE);
      });

      it('should not add a CandidatureE to an array that contains it', () => {
        const candidatureE: ICandidatureE = sampleWithRequiredData;
        const candidatureECollection: ICandidatureE[] = [
          {
            ...candidatureE,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCandidatureEToCollectionIfMissing(candidatureECollection, candidatureE);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CandidatureE to an array that doesn't contain it", () => {
        const candidatureE: ICandidatureE = sampleWithRequiredData;
        const candidatureECollection: ICandidatureE[] = [sampleWithPartialData];
        expectedResult = service.addCandidatureEToCollectionIfMissing(candidatureECollection, candidatureE);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(candidatureE);
      });

      it('should add only unique CandidatureE to an array', () => {
        const candidatureEArray: ICandidatureE[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const candidatureECollection: ICandidatureE[] = [sampleWithRequiredData];
        expectedResult = service.addCandidatureEToCollectionIfMissing(candidatureECollection, ...candidatureEArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const candidatureE: ICandidatureE = sampleWithRequiredData;
        const candidatureE2: ICandidatureE = sampleWithPartialData;
        expectedResult = service.addCandidatureEToCollectionIfMissing([], candidatureE, candidatureE2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(candidatureE);
        expect(expectedResult).toContain(candidatureE2);
      });

      it('should accept null and undefined values', () => {
        const candidatureE: ICandidatureE = sampleWithRequiredData;
        expectedResult = service.addCandidatureEToCollectionIfMissing([], null, candidatureE, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(candidatureE);
      });

      it('should return initial array if no CandidatureE is added', () => {
        const candidatureECollection: ICandidatureE[] = [sampleWithRequiredData];
        expectedResult = service.addCandidatureEToCollectionIfMissing(candidatureECollection, undefined, null);
        expect(expectedResult).toEqual(candidatureECollection);
      });
    });

    describe('compareCandidatureE', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCandidatureE(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCandidatureE(entity1, entity2);
        const compareResult2 = service.compareCandidatureE(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCandidatureE(entity1, entity2);
        const compareResult2 = service.compareCandidatureE(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCandidatureE(entity1, entity2);
        const compareResult2 = service.compareCandidatureE(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
