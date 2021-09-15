function Footpoints=getFoot(Lvalue,year,day,hour,N)
addpath(genpath('d:\documents\matlab\LDiscrete'));
longap=360/(2^N);
sL=num2str(Lvalue,'%.1f');
sL=strrep(sL,'.','_');

% time
syear=num2str(year);
sday=num2str(day,'%03d');
shour=num2str(hour,'%02d');
%smin=num2str(minute,'%02d');
time=[syear,sday,shour,'00'];

path=['D:\documents\java\out\L',sL,'_',syear,'\',sday,'\','N',num2str(N),'\'];
% get all the files of the same L value
name1=['JAVAOUT_',sL,'_'];
name2=['_',time,'_'];
%name3='1_IGRF.DAT';
name4='M1_IGRF.DAT';
%longap=0.1;

linesnum=360/longap;
Footpoints=zeros(linesnum,3);
index=1;
for i=0:(linesnum-1)
    slon=num2str(i);
    %filename1=[path,name1,slon,name2,name3];
    filename2=[path,name1,slon,name2,name4];
    
    %data1=load(filename1);% magcar, dir=1
    data2=load(filename2);% magcar, dir=-1
    
   %m=size(data1,1);
    n=size(data2,1);
    
   %Footpoints(index,1)=data1(m,1);
    %Footpoints(index,2)=data1(m,2);
    %Footpoints(index,3)=data1(m,3);
    % Footpoints 1,2,3; startpoints 4,5,6
    Footpoints(index,1)=data2(n,1);
    Footpoints(index,2)=data2(n,2);
    Footpoints(index,3)=data2(n,3);
    index=index+1;
end
end