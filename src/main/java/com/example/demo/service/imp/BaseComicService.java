package com.example.demo.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ComicInfoRepository;
import com.example.demo.dao.ComicViewRepository;
import com.example.demo.dao.ReaderRepository;
import com.example.demo.dao.UploaderRepository;
import com.example.demo.dao.UploaderStatisticRepository;
import com.example.demo.dao.UserManualRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.request.ComicRequest;
import com.example.demo.dto.request.ViewRequest;
import com.example.demo.dto.response.ComicResponse;
import com.example.demo.dto.response.PaginatedUserComics;
import com.example.demo.dto.response.TagResponse;
import com.example.demo.dto.response.UserComics;
import com.example.demo.dto.response.ViewDay;
import com.example.demo.dto.response.ViewsReport;
import com.example.demo.entity.ComicInfo;
import com.example.demo.entity.ComicView;
import com.example.demo.entity.Reader;
import com.example.demo.entity.Tag;
import com.example.demo.entity.User;
import com.example.demo.error.custom.CustomObjectAlreadyExist;
import com.example.demo.error.custom.CustomeObjectConstraintException;
import com.example.demo.error.custom.ObjectNotFoundException;
import com.example.demo.service.ComicService;

@Service("BaseComicService")
public class BaseComicService implements ComicService {

	@Autowired
	UserManualRepository userMRepo;
	
	@Autowired
	ComicInfoRepository comicInfoRepository;
	
	@Autowired
	UploaderRepository uploaderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ReaderRepository readerRepository;
	
	@Autowired
	ComicViewRepository comicViewRepository;
	
	@Autowired
	UploaderStatisticRepository uploaderStatisticRepository;
	
	@Override
	public PaginatedUserComics getUserComics(int pageSize, int pageIndex, String keyword) {
		
		if(pageIndex < 1) pageIndex = 1;
		
		PaginatedUserComics userComics = new PaginatedUserComics();
		userComics.setData(userMRepo.getUserComics(pageSize, pageIndex, keyword));
		userComics.setTotalPage(Math.ceil( (comicInfoRepository.getCountUserComics()*1.0) / (pageSize * 1.0)));
		userComics.setPageSize(pageSize);
		userComics.setCurrentPage(pageIndex);
		userComics.setSearch(keyword);
		return userComics;
	}
	
	@Override
	public ComicResponse saveComicDTO(ComicRequest comicDTO) {
		
		ComicInfo comicInfo = comicRequestToComicInfo(comicDTO);
		if(comicInfoRepository.existsByTitle(comicInfo.getTitle())) {
			throw new CustomObjectAlreadyExist("\"" + comicDTO.getTitle() + "\" title already exist");
		}
		
		try {
			
			comicInfo.setUploader(uploaderRepository.findById(comicDTO.getUploaderId()).get());
			comicInfo = comicInfoRepository.save(comicInfo);
			
		} catch (ConstraintViolationException ex) {
			throw new CustomeObjectConstraintException(ex);
		}
		
		return new ComicResponse(comicInfo);
	}

	//=============================================================================================================
	
	public ComicInfo comicRequestToComicInfo(ComicRequest comicRequest) {
		
		ComicInfo comicInfo ; 
		
		//Port list<TagResponse> to set<Tag>
		Set<Tag> list = new HashSet<>();
		if(!comicRequest.getTags().isEmpty()) {
			comicRequest.getTags().forEach(tag -> 
			list.add(new Tag(tag.getId(), tag.getName())));
		}
		
		if(!uploaderRepository.findById(comicRequest.getUploaderId()).isPresent()) {
			
			throw new ObjectNotFoundException("Not found uploader with ID: " + comicRequest.getUploaderId());
			
		}
		
		comicInfo = new ComicInfo();
		comicInfo.setTags(list);
		comicInfo.setTitle(comicRequest.getTitle().trim());
		comicInfo.setUploadTime(new Date());
		comicInfo.setArtist(comicRequest.getArtist());
		
		return comicInfo;
	}
	
	public List<ComicResponse> getAllComics(int id) {
		List<ComicResponse> list = new ArrayList<>();
		comicInfoRepository.findAll().forEach(comic -> {
			if(comic.getUploader().getId() == id) {
				comic.setUploader(comic.getUploader());
				list.add(new ComicResponse(comic));
			}
		});
		
		return list;
	}
	
//====================================================================================================
	@Override
	public ComicResponse getComicById(int id) {
		Optional<ComicInfo> comicInfo = comicInfoRepository.findById(id);
		if(!comicInfo.isPresent()) {
			throw new ObjectNotFoundException("Can not find comic with id "+id);
		}
		
		ComicResponse comicResponse = new ComicResponse(comicInfo.get());
		comicResponse.setComicFavorites(comicInfoRepository.countComicFavorites(id));
		
		return comicResponse;
	}
	
//=====================================================================================================
	@Override
	public ComicResponse updateComicInfoById(ComicRequest comicInfo , int comicId) {
		Optional<ComicInfo> comicInf = comicInfoRepository.findById(comicId);
		if(!comicInf.isPresent()) {
			throw new ObjectNotFoundException("Not found comic with id: " + comicId);
		} else {
			
			if(! comicInf.get().getTitle().equalsIgnoreCase(comicInfo.getTitle())) {
				if (comicInfoRepository.existsByTitle(comicInfo.getTitle())) {
					throw new CustomObjectAlreadyExist("\"" + comicInfo.getTitle() + "\" title already exist");
				}
			}
			
			comicInf.get().setTitle(comicInfo.getTitle());
			comicInf.get().setArtist(comicInfo.getArtist());
			Set<Tag> tags = new HashSet<>();
			
			comicInfo.getTags().forEach(tag -> 
				tags.add(new TagResponse().tagResponseToTag(tag))
			);
			
			comicInf.get().setTags(tags);
			comicInfoRepository.save(comicInf.get());
			
		}
		
		return new ComicResponse(comicInf.get());
		
	}

	@Override
	public List<UserComics> getUserFavorComics(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent()) {
			return userMRepo.getUserFavoriteComics(user.get().getId());
		} else {
			throw new ObjectNotFoundException("Not found user with username is: " + username);
		}
	}

	@Override
	public void countView(ViewRequest viewRequest) {
		Optional<Reader> reader = readerRepository.findByUuidAndComicInfoId(viewRequest.getUuid(), viewRequest.getComicId());
		if(!reader.isPresent()) {
			Reader newReader = readerRepository.save(new Reader(viewRequest.getUuid(), viewRequest.getComicId(), new Date().getTime() / 1000));
			comicViewRepository.save(new ComicView(newReader));
		} else if(reader.isPresent()) {
			if(ComicView.isCountLegit(reader.get().getLatestView())) {
				
				reader.get().setLatestView(new Date().getTime() / 1000);
				readerRepository.save(reader.get());
				comicViewRepository.save(new ComicView(reader.get()));
			
			} else {
				System.out.println("Eto...Sumimasen");
			}
		}
	}

	@Override
	public ViewsReport getViewReport(int comicId) {
		
		List<ViewDay> viewList = 
				uploaderStatisticRepository.getComicViewsPrevious7Days(comicId);
		
		ViewsReport viewsReport = new ViewsReport();
		viewsReport.setViewDays(viewList);
		viewsReport.setTotalViews(comicInfoRepository.getTotalView(comicId));
		
		return viewsReport;
		
	}
	
	
}
