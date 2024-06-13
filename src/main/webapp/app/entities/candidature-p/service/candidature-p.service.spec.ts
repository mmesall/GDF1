import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICandidatureP } from '../candidature-p.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../candidature-p.test-samples';

import { CandidaturePService, RestCandidatureP } from './candidature-p.service';

const requireRestSample: RestCandidatureP = {
  ...sampleWithRequiredData,
  dateDebutOffre: sampleWithRequiredData.dateDebutOffre?.format(DATE_FORMAT),
  dateFinOffre: sampleWithRequiredData.dateFinOffre?.format(DATE_FORMAT),
  dateDepot: sampleWithRequiredData.dateDepot?.format(DATE_FORMAT),
};

describe('CandidatureP Service', () => {
  let service: CandidaturePService;
  let httpMock: HttpTestingController;
  let expectedResult: ICandidatureP | ICandidatureP[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CandidaturePService);
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

    it('should create a CandidatureP', () => {
      const candidatureP = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(candidatureP).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CandidatureP', () => {
      const candidatureP = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(candidatureP).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CandidatureP', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CandidatureP', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CandidatureP', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCandidaturePToCollectionIfMissing', () => {
      it('should add a CandidatureP to an empty array', () => {
        const candidatureP: ICandidatureP = sampleWithRequiredData;
        expectedResult = service.addCandidaturePToCollectionIfMissing([], candidatureP);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(candidatureP);
      });

      it('should not add a CandidatureP to an array that contains it', () => {
        const candidatureP: ICandidatureP = sampleWithRequiredData;
        const candidaturePCollection: ICandidatureP[] = [
          {
            ...candidatureP,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCandidaturePToCollectionIfMissing(candidaturePCollection, candidatureP);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CandidatureP to an array that doesn't contain it", () => {
        const candidatureP: ICandidatureP = sampleWithRequiredData;
        const candidaturePCollection: ICandidatureP[] = [sampleWithPartialData];
        expectedResult = service.addCandidaturePToCollectionIfMissing(candidaturePCollection, candidatureP);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(candidatureP);
      });

      it('should add only unique CandidatureP to an array', () => {
        const candidaturePArray: ICandidatureP[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const candidaturePCollection: ICandidatureP[] = [sampleWithRequiredData];
        expectedResult = service.addCandidaturePToCollectionIfMissing(candidaturePCollection, ...candidaturePArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const candidatureP: ICandidatureP = sampleWithRequiredData;
        const candidatureP2: ICandidatureP = sampleWithPartialData;
        expectedResult = service.addCandidaturePToCollectionIfMissing([], candidatureP, candidatureP2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(candidatureP);
        expect(expectedResult).toContain(candidatureP2);
      });

      it('should accept null and undefined values', () => {
        const candidatureP: ICandidatureP = sampleWithRequiredData;
        expectedResult = service.addCandidaturePToCollectionIfMissing([], null, candidatureP, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(candidatureP);
      });

      it('should return initial array if no CandidatureP is added', () => {
        const candidaturePCollection: ICandidatureP[] = [sampleWithRequiredData];
        expectedResult = service.addCandidaturePToCollectionIfMissing(candidaturePCollection, undefined, null);
        expect(expectedResult).toEqual(candidaturePCollection);
      });
    });

    describe('compareCandidatureP', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCandidatureP(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCandidatureP(entity1, entity2);
        const compareResult2 = service.compareCandidatureP(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCandidatureP(entity1, entity2);
        const compareResult2 = service.compareCandidatureP(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCandidatureP(entity1, entity2);
        const compareResult2 = service.compareCandidatureP(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
