package edu.cmu.lti.algorithm.string.editdistance;

import java.util.ArrayList;


public class EditDistanceSparseDP extends AEditDistance {

		public static class TLinkNode	{
			int is1,is2,ifather;
			TLinkNode(int is1,int is2,int ifather){
				this.is1=is1;
				this.is2=is2;
				this.ifather=ifather;
			}
		};
		int[] vML; //O(n) chains of match lists, has the length of that of s2
		int[] vMLhead; //O(c) tail pointer of match list for each character, has the length as the size of vocabulary
		int[] vTH; //O(n) for the first x chars of s1 to have y matches with s2, we need at least the first vTH[y] chars of s2
		int[] vTH2LNK; //O(n) each threshold must have be generated by a match event--logged by a link.
		ArrayList<TLinkNode> vLNK; //<O(r) location of s1 that corresponds to the matche in vTH

		void ClearTemp(){
/*			vML.clear();
			vMLhead.clear();
			vTH.clear();
			vTH2LNK.clear();
			vLNK.clear();*/
			vLNK.clear();
		}


	/*	VectorI s1,s2;
		int iScore;
		
		VectorI  ans;
*/

		public int Align()	{
			//LIS method, need a lot of  memory when vocabulary is small as in bioinformatic
			//TRACE("\nlcs|s1|=%d |s2|=%d  ", s1.size(),s2.size());
			//if (bCensusS2) {CensusS2();}
			CensusS2();
			SparseDP();
			RestoreAns();
			return nMatch;	
		}

		void CensusS2( )	{
			CensusS2(0);
		}
		void CensusS2(int iVolcaSize )	{
			vML= new int[s2.length];//.resize(s2.size());
			if (iVolcaSize <=0) {
				for (int i =0; i<s1.length; ++i) 
					if (s1[i] >= iVolcaSize) 
						iVolcaSize = s1[i] +1;
				for (int i =0; i<s2.length; ++i) 
					if (s2[i] >= iVolcaSize) 
						iVolcaSize = s2[i] +1;
			}
			vMLhead= new int[iVolcaSize];//.resize(iVolcaSize);
			for (int i=0; i<vMLhead.length;++i) 
				vMLhead[i] = -1;

			for (int i=0; i<s2.length;++i){
				vML[i] = vMLhead[s2[i]];
				vMLhead[s2[i]] = i;
			}
			/*
		#ifdef _DEBUG
			TRACE("\nvML ");for (int i=0; i< vML.size(); ++i)	TRACE("%d ",vML[i]);
			TRACE("\nvMLhead ");for (int i=0; i< vMLhead.size(); ++i)	TRACE("%d ",vMLhead[i]);
		#endif
		*/
		}
		boolean biSearchInc(int[] v, int l, int r,int val, int p){
			if (val < v[0]){
				p = 0;
				return false;
			}
			if (val > v[v.length-1]){
				p = v.length;
				return false;
			}

			int m;
			do{
				m = (r + l) /2;
				if (v[m] == val){
					p = m; 
					return true;
				}

				if (v[m]  < val)
					l = m;
				else 				
					r = m;
			}
			while ( l+1< r  );

			p = r;
			return false;
		}

		void SparseDP()	{
/*			vTH.resize(s1.length);
			for (int i=0; i<vTH.length;++i) vTH[i] = s2.size();

			vTH2LNK.resize(s1.size()+1);vTH2LNK[0] = -1;
			//for (int i=0; i<vTH2LNK.size();++i) vTH2LNK[i] = s2.size();

			vLNK.clear();

			int p ;
			int lastmatch;int lasti=-1;
			for (int i=0; i<s1.size(); ++i)
			{
				p = i;
				lastmatch =-1;
				for (int j=vMLhead[s1[i]]; j !=-1; j= vML[j])
				{
					
					if (!biSearchInc(vTH,0,p,j,p))
					{
						vTH[p] = j;

						
						if(lastmatch == p&& i==lasti)
						{
							vLNK.back().is2 = j;
						}
						else
						{

							vLNK.push_back(TLinkNode(i,j,vTH2LNK[p] ));
							vTH2LNK[p+1] = vLNK.size()-1;
						}

						lastmatch = p;
						lasti = i;
					}

				}

			}
*/

		}
		void SparseDP2(){
	/*		vTH.resize(s1.size());
			for (int i=0; i<vTH.size();++i) vTH[i] = s2.size();

			vTH2LNK.resize(s1.size()+1);vTH2LNK[0] = -1;
			//for (int i=0; i<vTH2LNK.size();++i) vTH2LNK[i] = s2.size();

			int nMatch=-1, ib;
			vLNK.clear();

			int p ;
			int j;
			for (int i=0; i<s1.size(); ++i)
			{
				if (nMatch>=0)
				{
					ib = vTH[nMatch];
				}
				else
				{
					ib = -1;
				}
				for (j=vMLhead[s1[i]]; j !=-1 ; j= vML[j])
				{
					if (vML[j] <= ib) break;
				}
				if (j>ib) ++ nMatch;

				p = i;
				for (; j !=-1; j= vML[j])
				{

					if (!biSearchInc(vTH,0,p,j,p))
					{
						for ( ;vML[j]>=0 && p >0;j = vML[j])
						{
							if (vML[j] <= vTH[p-1]) break;
							j = vML[j];
						}
						vTH[p] = j;
						vLNK.push_back(TLinkNode(i,j,vTH2LNK[p] ));
						vTH2LNK[p+1] = vLNK.size()-1;
					}

				}

			}

*/
		}
		void RestoreAns()	{
			int i;
			for ( i=vTH.length-1; i>=0; --i)	{
				if (vTH[i]<s2.length) {
					for (int j=vTH2LNK[i+1]; j>=0; ){
						TLinkNode link= vLNK.get(j);
						ans[link.is1] = link.is2;
						j = link.ifather;
					}
					break;
				}
			}
			nMatch = i+1;
		}

}
