;**
;**	Logics for room 340 -- Comedy shop
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 24, 1989
;**


(script#				340)

(public
	rm340				0
)


(define	LAST_JOKE					 42)

(define	vComedyHut					340)
	(define 	lATPix 				0)
		(define	cEmptyChair	1)
		(define	cManUL		1)
		(define	cLadyUL		2)
		(define	cLadyLR		4)
		(define	cBottle		5)
		(define	cMan1Up		7)
		(define	cMan2Up		8)
		(define	cWoman1Up	9)
	(define 	lManUL	 			1)
	(define 	lManLL	 			1)
	(define 	lLadyUL				2)
	(define 	lLadyLL				3)
	(define 	lLadyLR				4)
	(define 	lWomanLeft1			5)
	(define	lSign					5)

(define	vPaul							343)
	(define	lTalking				4)
		(define	cLastTalk	2)

(define	vPaulDuck					344)
	(define	lPaul2duck			2)

(define	vDrummer						345)
	(define	lFill					0)
	(define	lTime					1)
	(define	lTimpRoll			2)

(define	vAlandBill					346)
	(define 	lBodies				0)
		(define	cBill			0)
		(define	cAl			1)
	(define 	lBillMouth			1)
	(define 	lAlHand				2)
	(define 	lAlMouth				3)
	(define	lBillPoof			4)
	(define	lAlPoof				5)
		(define	cPoofDone	4)

;(define	vLdark						705)
  (define  lFromLeft				4)
  (define  lFromRight			5)

;(define	vLdarkHunk					725)


;(define	sTimpaniRoll				010)
;(define	sDrumFill1					021)
(define	sDrumFill2					022)
(define	sDrumFill3					023)
(define	sDrumFill4					024)
(define	sDrumFill5					025)
(define	sDrumFill6					026)
(define	sDrumFill7					027)
(define	sComedyPlayon				340)
;(define	sComedyHut					341)


(define	BillBottomX				 70)
(define	BillBottomY				190)
(define	BillTopX					 67)
(define	BillTopY					155)
(define	AlTopX					102)
(define	AlTopY					148)
(define	AlBottomX				 99)
(define	AlBottomY				191)
(define 	LadyUL_TopX	 			 45)
(define 	LadyUL_TopY				104)

(define 	LadyUL_BottomX	 		 52)
(define 	LadyUL_BottomY			133)

(define 	LadyLR_TopX	 			291)
(define 	LadyLR_TopY				156)

(define 	LadyLR_BottomX	 		283)
(define 	LadyLR_BottomY			185)

(define 	ManUL_TopX	 			281)
(define 	ManUL_TopY				104)

(define 	ManUL_BottomX	 		275)
(define 	ManUL_BottomY			132)

(define	nearChair 				(& (ego onControl) cBlue))
(define	nearBottle 				(& (ego onControl) cLtGray))
(define	nearAl					(& (ego onControl) cCyan))
(define	nearBill 				(& (ego onControl) cGreen))

(define	IN_LIGHT_X			125 195)
(define	IN_LIGHT_Y			 66  80)



(enum												;**	ComicScript state values
	comicIntro
	startComic
	andNow
	startPaulWalking
	comicAtCenter
	stopSign
	comicPause
	ComicDoneTalking
	goodEvening
	getEthnicNames
	doNextJoke
	tellAjoke
	comicMiddle
	beginDuck
	waitB4duck
	beginTransform
	transformed
	duckWalk
	duckLeft
	backToPaul
	slightDelay
	ladies&Gentlemen
	comicLeaving
	byeComic
	comicDone
)


(enum												;**	RoomScript state values
	intro
	gotoTable
	atTable
	sitting
	gettingUp
	standing
	startAuthors
	alSpeaks
	anIdea
	billTalks
	noWay
	AlGestures
	AlTalks
	poofAl
	poofDone
)


(enum												;**	drinkerScript state values
	drinkerInit
	drinkerStart
	smallDelay
	drinkerMiddle
)


(local
	[toldJoke	(+ 3 LAST_JOKE)]			;**	a little extra never hurts
	testCount
	currentDrinker
	whichLoop
	comicOnStage
	[string  30]
	[ethnicGroup1 10] = 0
	[ethnicGroup2 10] = 0
	[ethnicGroup3 10] = 0
	group1
	group2
	group3
)


(instance rm340 of Room
	(properties
		picture		340
		horizon		1
		south			240
	)

	(method (init)
		(Load TEXT 341)

		(Load VIEW vLdark)					;**	even IF hunk
		(Load VIEW vPaulDuck)

		(LoadMany SOUND 
			sDrumFill1
			sDrumFill2
			sDrumFill3
			sDrumFill4
			sDrumFill5
			sDrumFill6
			sDrumFill7
			sTimpaniRoll
			sComedyPlayon
			sComedyHut
		)

		(= egoState IN_DARK)

		(super init)

		(addToPics
			add 				atpChair,
			add 				atpManUR,
			add 				atpManLR,
			add 				atpManUL_Bottom,
			add 				atpLadyLR_Bottom,
			add 				atpLadyUL_Bottom,
			doit,
		)

		(aManUL_Top   		init)
		(aLadyUL_Top		init)
		(aLadyLR_Top 		init)
		(aDrummer 			init)
		(aComic				init)
		(aSign				init)

		(if (and	(InRoom iBottle)
					(ego has iKey))
			(aBottle
				setPri		10,
				ignoreActors,
				init,
			)
		)

		(self
			setScript 		RoomScript
		)

		(NormalEgo facingNorth (+ vLdark egoIsHunk))
		(ego 
			posn 				159 188,
			init,
		)

		(if (TstFlg sawAl&BillPoof)
			(aAl 					
				loop				lAlPoof,		;**	This makes Al's empty chair.
				cel				cPoofDone,
				init,
				stopUpd,
			)
			(aBill	 			
				loop				lBillPoof,	;**	This makes Bill's empty chair.
				cel				cPoofDone,
				init,
				stopUpd,
			)
		else
			(aAlTop	 			init)
			(aBillTop	 		init)
			(aAl 					init)
			(aBill	 			init)
		)
	)


	(method (newRoom n)
		(if comicOnStage
			(Print "\"Hey, you!\" shouts the comic.
				\"Come back when you get a sense of humor!\"")
		)
		(= egoState NORMAL)
		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'boo')
				(if comicOnStage
					(Print "\"Hey, Paul,\" you shout,
						\"that joke's so old it's got gray hair!")
					(Printf "\"Oh, yeah,\" retorts the comic,
						\"%s\"" expletive)
					(Print "(Some guys just don't have a way with hecklers!)"
						LOW)
				else
					(Print "Wait until the comic is onstage.")
				)
			)

			((Said 'cheer')
				(if comicOnStage
					(Print "\"Hey, Paul,\" you shout,
						\"that last joke was mildly amusing!")
				else
					(Print "Wait until the comic is onstage.")
				)
			)

			((and debugging
					(Said 'test/joke'))
				(= testCount (GetNumber {First joke (from 1 to LAST_JOKE):}))
				(Printf "Ok. Next joke will be #%d." testCount)
			)

			((Said 'talk/comic')
				(if comicOnStage
					(Print "\"Hey, buddy,\" you shout,
						\"that material came over on the `Bounty!'\"")
				else
					(Print "Paul's not here. He's successfully escaped again!")
				)
			)

			((Said 'talk/drummer')
				(Print "\"Hey, Bob! Don't give up your day gig!\"")
			)

			((or	(Said 'get/microphone')
					(Said 'stand, get, jump, climb<on/stage'))
				(Print "From the looks of your input,
					you're no comedian!")
			)

			((Said 'talk/man, bill, girl, al')
				(cond
					((or	nearAl
							nearBill)
						(if (TstFlg sawAl&BillPoof)
							(Print "Where?")
						else
							(self changeState startAuthors)
						)
					)
					(else
						(Printf "These people are here to hear the comedian,
							not you. Find a seat, %s, and enjoy yourself." egoName)
					)
				)
			)

			((Said 'get/bottle, wine')
				(cond
					((and (!= egoState IN_DARK)
							(!= egoState SITTING))
						(NotNow)
					)
					((or	(not (InRoom iBottle))
							(not (ego has iKey)))
						(Print "Where?")
					)
					((not nearBottle)
						(NotClose)
					)
					(else
						(Ok)
						(aBottle 	dispose)
						(theGame changeScore 15)
						(Print "You quietly cop some poor slob's
							bottle of `fine' wine.")
						(ego
							get 		iBottle,
						)
					)
				)
			)

			((or	(Said 'rise, (get<off), (get<up), (stand<up)')
					(Said 'leave/chair'))
				(cond
					((== egoState IN_DARK)
						(YouAre)
					)
					((!= egoState SITTING)
						(NotNow)
					)
					(else
						(self changeState gettingUp)
					)
				)
			)

			((Said 'sit')
				(cond
					((not nearChair)
						(Print "Move in front of the chair that's at the center
							table.")
					)
					((== egoState SITTING)
						(YouAre)
					)
					((!= egoState IN_DARK)
						(NotNow)
					)
					(else
						(self changeState gotoTable)
					)
				)
			)

			((Said 'look>')
				(cond
					((Said '/chair')
						(Print "\"The world-famous \"Comedy Hut\" has one empty table and chair,
							and it's right in the center of the room!\"")
					)

					((Said '/stage')
						(Print "The stage here is not too big, but it's dark!")
					)

					((Said '/mask')
						(Print "One represents \"Comedy,\" 
							the second \"Tragedy,\"
							and the third \"Caffeine,\"
							but you can never remember which is which!")
					)

					((Said '/sign')
						(Print "The sign is the most impressive aspect of this
							place!")
					)

					((Said '/cigarette, smoke')
						(Print "There's always got to be someone!")
					)

					((Said '/light, spotlight')
						(Print "The spotlight spotlights the stage.")
					)

					((or	(Said 'order/wine, drink, booze')
							(Said 'order//wine, drink, booze')
							(Said '/waiter, waitress, bartender')
							(Said '//waiter, waitress, bartender'))
						(Print "It's impossible to get a drink around here!")
					)

					((Said '/door')
						(Print "The door to the outside is to the south.")
					)

					((Said '/comic')
						(cond
							(comicOnStage
								(Print "Look! It's Paul Paul!")
							)
							(else
								(Print "He's backstage, trying to rid himself
									of the odor of flop sweat!")
							)
						)
					)

					((Said '/lady')
						(Print "There are several women sitting
							here in the \"Comedy Hut,\"
							but none that are interested in talking to you.")
					)

					((Said '/man, people')
						(if (and	(not (TstFlg sawAl&BillPoof))
									(or 	nearAl
											nearBill))
							(Print "Sitting at this table are Bill Skirvin
								and Al Lowe,
								deep in an esoteric discussion about
								3-D animated graphic adventure game design.")
						else
							(Print "There are several men sitting
								here in the \"Comedy Hut,\"
								but none that are interested in talking to you.")
						)
					)

					((Said '/bottle')
						(if (and	(InRoom iBottle)
									(ego has iKey))
							(Print "A bottle of wine rests on the center table,
								breathing until its owner returns.")
						else
							(Print "Where?")
						)
					)

					((Said '/al')
						(cond
							((TstFlg sawAl&BillPoof)
								(Print "He is not here.")
								(Print "(He has \"poofed!\")" LOW)
							)
							(nearAl
								(Print "Al Lowe is trying (unsuccessfully)
									to make some point with Bill Skirvin.")
							)
							(else
								(Print "Hey! Is that Al Lowe over there?")
							)
						)
					)

					((Said '/bill')
						(cond
							((TstFlg sawAl&BillPoof)
								(event claimed FALSE)
							)
							(nearBill
								(Print "William Skirvin is trying to explain the
									subtleties of computer graphics with Al Lowe.")
							)
							(else
								(Print "Hey! Is that Bill Skirvin over there?")
							)
						)
					)

					((Said '/table')
						(cond
							((and	nearBottle
									(InRoom iBottle)
									(ego has iKey))
								(Print "A bottle of wine rests on the center table,
									breathing until its owner returns.")
							)
							(else
								(Print "The world-famous \"Comedy Hut\"
									is filled with tables,
									many of which have people sitting at them
									enjoying themselves.
									Why don't you sit at that empty table in the
									center of the room.")
							)
						)
					)

					((Said '/drummer')
						(cond
							(comicOnStage
								(Print "He always plays the same fill.")
							)
							(else
								(Print "He looks terminally bored.")
							)
						)
					)

					((Said '[/room, people]')
						(Print "The world-famous \"Comedy Hut\" is filled with people
							having a mildly uproarious time.")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	Larry sits down

			(gotoTable
			 	(Ok)
				(HandsOff)
				(if (< (ego x?) 165)
					(ego
						illegalBits		0,
						setMotion 		MoveTo 153 145 self
						)
					(= whichLoop lFromLeft)
				else
					(ego
						illegalBits		0,
						setMotion  		MoveTo 176 145 self)
					(= whichLoop lFromRight)
				)
			)

			(atTable
				(delay 0)
				(= cycles 0)
				(ego
					view 					vLdark,
					loop					whichLoop,
					cel					0,
					illegalBits 		0,
;					ignoreActors,
					setCycle				EndLoop self,
				)
			)

			(sitting
				(if (== (ComicScript state) 0)
					(ComicScript changeState startComic)
				)
				(User canInput TRUE)
				(= egoState SITTING)
			)

			(gettingUp
				(ego
					setCycle				BegLoop self,
				)
			)

			(standing
				(= egoState IN_DARK)
				(NormalEgo facingNorth (+ vLdark	egoIsHunk))
				(if comicOnStage
					(Print "\"Hey, you!\" shouts the comic.
						\"Don't you like my material?\"")
				)
			)


			;**	"If I could talk to the animals" OR talk to Al & Bill 

			(startAuthors
				(HandsOff)
				(= string 0)
				(while ( > 5 (StrLen @string))
					(GetInput @string 50 {Just say what you want to say:}
						#title			{Author Interface}
					)
				)
				(aAlTop
					loop				lAlHand,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(alSpeaks
				(aAlTop
					loop				lAlMouth,
					setCycle			Forward,
				)
				(delay 3)
			)

			(anIdea
				(Printf "\"Hey! I've got an idea.
					How about if you and I sit in a comedy club
					and we make Larry walk up to us and say something like,
					`%s'\"" @string)
				(aAlTop
					loop				lAlHand,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(billTalks
				(aBillTop			show)
				(delay 3)
			)

			(noWay
				(Printf "\"`%s', eh?\" says Bill,
					\"Are you crazy? No way. That's so lame!
					Larry would never say that!\"" @string)
				(delay 3)
			)

			(AlGestures
				(aBillTop			hide)
				(aAlTop
					setCycle			EndLoop self,
				)
			)

			(AlTalks
				(aAlTop
					loop				lAlMouth,
					setCycle			Forward,
				)
				(delay 2)
			)

			(poofAl
				(Printf "\"Yeah, I suppose you're right,\" says Al.
					\"This is not a good idea. Let's get out of here!
					`%s' Really!!\"" @string)
				(aAl					dispose)
				(aBill				dispose)
				(aAlTop
					posn				AlBottomX AlBottomY,
					loop				lAlPoof,
					cel				0,
					cycleSpeed		1,
					setCycle			EndLoop,
				)
				(aBillTop
					posn				BillBottomX BillBottomY,
					loop				lBillPoof,
					cel				0,
					cycleSpeed		1,
					show,
					setCycle			EndLoop self,
				)
			)

			(poofDone
				(HandsOn)
	  			(aAlTop				stopUpd)	;**	They're just a chair now.
	  			(aBillTop			stopUpd)
				(if (not (TstFlg sawAl&BillPoof))
					(SetFlg sawAl&BillPoof)
					(theGame changeScore	5)
				)
			)
		)
	)
)


(instance ComicScript of Script
	(method (doit)
		(super doit)

		(if (and (== SND_DONE (music prevSignal))
					(== state ComicDoneTalking))
			(self cue)
		)
	)


	(method (changeState newState 
					&tmp [jokeString 200] [jokeArgs 4] count line i j k )
		(switch (= state newState)

			(comicIntro
			)

			(startComic
				(if (> machineSpeed pcAT)
					(aSign
						setCycle			Forward,
					)
				)
				(music
	 				number			sTimpaniRoll,
					loop				FOREVER,
					play,
				)
				(aDrummer
					loop				lTimpRoll,
					setCycle			Forward,
				)
				(delay 4)
			)

			(andNow
				(Print "\"And now, ladies and gentlemen,
					the world-famous `Comedy Hut' is proud to present,
					a legend in his own mind, Paul Paul!\"")
				(if (!= egoState SITTING)
					(Print "From the darkness,
						someone in the audience yells at you,
						\"Hey you! Yeah, you standing up! Sit down, willya?\"")
				)
				(music
					stop,
					number			sComedyPlayon,
					loop				1,
					play ,
				)
				(aDrummer
					cycleSpeed		1,
					loop				lTime,
				)
				(delay 3)
			)

			(startPaulWalking
				(aComic
					setMotion		MoveTo 160 58 self
				)
			)

			(comicAtCenter
				(aComic
					setPri			RELEASE,
					setMotion		MoveTo 160 78 self
				)
			)

			(stopSign
				(if (> machineSpeed pcAT)
					(aSign
						setCel			1,		;**	sign stays lit
						stopUpd,
					)
				)
				(= comicOnStage TRUE)
				(delay 3)
			)

			(comicPause
				(aComic
					loop				lTalking,
					viewer			talkCycler,
				)
				(delay 3)
			)

			(ComicDoneTalking
				;**	just in case
			)

			(goodEvening
				(aDrummer
					cycleSpeed		0,
					loop				lFill,
					setCel			0,
				)
				(Print "\"Good evening, ladies and germs,\" says Paul,
					always the snappy starter.")
				(delay 3)
			)

			(getEthnicNames
				(music
					number			sComedyHut,
					loop				FOREVER,
					play,
				)
				(if (!= egoState SITTING)
					(Print "The comic looks straight at you.
						\"Sit down, dork!\"")
				)
				(Print "\"Ok, folks, we're gonna try something different tonight.
					Tell me the name of your three favorite ethnic groups.
					It doesn't matter to me which you choose;
					I've got enough jokes to insult anyone!\"")
				(= ethnicGroup1 0)
				(= ethnicGroup2 0)
				(= ethnicGroup3 0)
				(while (u> 3 (StrLen @ethnicGroup1))
					(GetInput @ethnicGroup1 15 {Ethnic group #1:})
				)
				(while (u> 3 (StrLen @ethnicGroup2))
					(GetInput @ethnicGroup2 15 {Ethnic group #2:})
				)
				(while (u> 3 (StrLen @ethnicGroup3))
					(GetInput @ethnicGroup3 15 {Ethnic group #3:})
				)
				(Print "\"Thank you.\"")
				(delay 3)
			)

			(doNextJoke
				(aComic
					loop				lTalking,
					viewer			talkCycler,
				)
				(delay 3)
			)

			(tellAjoke
				(aComic
					setCycle			Walk,
					viewer			FALSE,
				)
				(= count 0)
				(repeat
					(++ count)
					(= i (Random 0 LAST_JOKE))

					(if testCount				;**	debugging code
						(= i (++ testCount))
						(= [toldJoke i] FALSE)
					)

					(if (== [toldJoke i] FALSE)
						(= [toldJoke i] TRUE)

						;**	Pick groups in random order & prevent dupes.
						(= group1 (Random `1 `3))
						(= group2 group1)
						(= group3 group1)
						(while (== group1 group2)
							(= group2 (Random `1 `3))
						)
						(while (or	(== group3 group1)
										(== group3 group2))
							(= group3 (Random `1 `3))
						)
						;**	Print five strings (unless string starts with a space)
						(for ((= j 0)) ( < j 5) ((++ j))
							(= k 0)
							(Format @jokeString "\"%s\"" 341 (+ j (* i 5)))
							(if (!= SPACEBAR (StrAt @jokeString 1))
								(for ((= line 0)) (< line (StrLen @jokeString)) ((++ line))
									(if (== `/ (StrAt @jokeString line))
										(StrAt @jokeString line `%)
										(switch (StrAt @jokeString (++ line))
											(group1
												(= [jokeArgs k] @ethnicGroup1)
												(++ k)
											)
											(group2
												(= [jokeArgs k] @ethnicGroup2)
												(++ k)
											)
											(group3
												(= [jokeArgs k] @ethnicGroup3)
												(++ k)
											)
										)
										(StrAt @jokeString line `s)
										(++ line)
									)
								)
								(Printf @jokeString [jokeArgs 0] [jokeArgs 1] 
									[jokeArgs 2] [jokeArgs 3] [jokeArgs 4] )
							)
						)

						(if (> testCount LAST_JOKE)
							(if testCount
								(Print "All done.")
								(= testCount 0)
							else
								(Print "error")
							)
						)
						(aDrummer
							setCycle			EndLoop,
						)
						(if ( > (DoSound NumVoices) 5)
							(soundFX
	 							number			(Random sDrumFill1 sDrumFill7),
								loop				1,
								play,
							)
						)
						(break)
					)

					(if (>= count 1000)
						(= state (- beginDuck 1))
						(break)
					)
				)
				(delay 2)
			)

			(comicMiddle
				(aComic
					setMotion MoveTo (Random IN_LIGHT_X) (Random IN_LIGHT_Y) self
				)
				(= state (- doNextJoke 1))
			)


			;**	Since you've heard all his jokes, Paul turns into a duck.

			(beginDuck
				(music				fade)
				(Print "\"And now,\" says the comic,
					\"because you've been so patient,
					I'd like to do my famous impersonation of a duck!\"")
				(aComic
					setMotion		MoveTo 158 75 self)
			)

			(waitB4duck
				(Print "\"And it goes something like this...\"")
				(if (not (TstFlg scoredDuckPoints))
					(SetFlg scoredDuckPoints)
					(theGame changeScore 100)
				)
				(delay 2)
			)

			(beginTransform
				(aComic
					view				vPaulDuck,
					loop				lPaul2duck,
					cel				0,
					setCycle			EndLoop self,
				   setMotion		FALSE,
				)
			)

			(transformed
				(aComic
					setCycle			Walk,
					setMotion		MoveTo 125 75 self,
				)
			)

			(duckWalk
				(aComic
					setMotion 		MoveTo 194 75 self
				)
			)

			(duckLeft
				(aComic
					setMotion		MoveTo 161 75 self
				)
			)

			(backToPaul
				(aComic
					loop				lPaul2duck,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(slightDelay
				(aComic
					view				vPaul,
					loop				facingSouth,
					setCycle			Walk,
				)
				(delay 2)
			)

			(ladies&Gentlemen
				(Print "\"Ladies and gentlemen, Paul Paul!\"")
				(music
					number			sComedyPlayon,
					loop				FOREVER,
					play,
				)
				(if (> machineSpeed pcAT)
					(aSign
						setCycle			Forward,
					)
				)
				(delay 2)
			)

			(comicLeaving
				(aComic
					setMotion		MoveTo 160 57 self
				)
			)

			(byeComic
				(Print "Look! He's getting a sitting ovation!")
				(aComic
					setMotion		MoveTo 241 58 self
				)
				(music				fade)
				(= comicOnStage FALSE)
			)

			(comicDone
				(aComic				hide)
				(if (> machineSpeed pcAT)
					(aSign
						setCel			0,
						stopUpd,
					)
				)
				(aDrummer			stopUpd)
				(music
					number			sComedyHut,
					loop			bgMusicLoops,
					play,
				)
			)
		)
	)
)


(instance drinkerScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(drinkerInit
				(delay (Random 2 10))
			)

			(drinkerStart
				(switch (Random 1 3)
					(1 (= currentDrinker aLadyLR_Top))
					(2 (= currentDrinker aLadyUL_Top))
					(3 (= currentDrinker aManUL_Top))
				)
				(currentDrinker
					setCycle			EndLoop self,
				)
			)

			(smallDelay
				(if (== currentDrinker aLadyUL_Top)	;**		The smoker
					(= state readyToStartOver)			;**	skips the next step.
				)
				(= cycles (Random 5 22))
			)

			(drinkerMiddle
				(currentDrinker
					setCycle			BegLoop self,
				)
				(= state readyToStartOver)
			)
		)
	)
)


(instance atpManUR of PicView
	(properties
		view				vComedyHut
		cel				cWoman1Up
		x	 				247
		y					186
	)
)

(instance atpManLR of PicView
	(properties
		view				vComedyHut
		cel				cMan2Up
		x	 				249
		y					128
	)
)

(instance atpChair of PicView
	(properties
		view				vComedyHut
		x	 				165
		y					145
		priority			11
	)
)

(instance aDrummer of Prop
	(properties
		view				vDrummer
;		loop				lDrummer
		x	 				79
		y					71
	)
	(method (init)
		(super init)
		(self
			stopUpd,
		)
	)
)

(instance aBottle of View
	(properties
		view				vComedyHut
		cel				cBottle
		x					165
		y					119
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aBillTop of Prop
	(properties
		view				vAlandBill
		loop				lBillMouth
		x					BillTopX
		y					BillTopY
	)
	(method (init)
		(super init)
		(self
			setCycle			Forward,
			setPri		  	14,
			ignoreActors,
			hide,
		)
	)
)
(instance aBill of View
	(properties
		view				vAlandBill
		loop				lBodies
		cel				cBill
		x	 				BillBottomX
		y					BillBottomY
		priority			14
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aAlTop of Prop
	(properties
		view				vAlandBill
		loop				lAlMouth
		x	 				AlTopX
		y					AlTopY
	)
	(method (init)
		(super init)
		(self
			setPri		14,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aAl of View
	(properties
		view				vAlandBill
;		loop				lBodies
		cel				cAl
		x					AlBottomX
		y					AlBottomY
	)
	(method (init)
		(super init)
		(self
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aLadyUL_Top of Prop
	(properties
		view				vComedyHut
		loop				lLadyUL
		x					LadyUL_TopX
		y					LadyUL_TopY
	)
	(method (init)
		(super init)
		(self
			setPri		9,
			stopUpd,
		)
	)
)
(instance atpLadyUL_Bottom of PicView
	(properties
		view				vComedyHut
		cel				cLadyUL
		loop				lATPix
		x					LadyUL_BottomX
		y					LadyUL_BottomY
	)
)

(instance aLadyLR_Top of Prop
	(properties
		view				vComedyHut
		loop				lLadyLR
		x	 				LadyLR_TopX
		y	 				LadyLR_TopY
	)
	(method (init)
		(super init)
		(self
			setPri		14,
			setScript	drinkerScript,
			stopUpd,
		)
	)
)
(instance atpLadyLR_Bottom of PicView
	(properties
		view				vComedyHut
		cel				cLadyLR
		loop				lATPix
		x					LadyLR_BottomX
		y					LadyLR_BottomY
	)
)

(instance aManUL_Top of Prop
	(properties
		view				vComedyHut
		loop				lManUL
		x	 				ManUL_TopX
		y	 				ManUL_TopY
	)
	(method (init)
		(super init)
		(self
			setPri		9,
			stopUpd,
		)
	)
)

(instance atpManUL_Bottom of PicView
	(properties
		view				vComedyHut
		cel				cManUL
		loop				lATPix
		x					ManUL_BottomX
		y					ManUL_BottomY
	)
)

(instance talkCycler of Code
	(method (doit)
		(if (Random 0 3)
			(aComic
				cel		(Random 0 cLastTalk),
			)
		)
	)
)
(instance aComic of Actor
	(properties
		view					vPaul
		x						241
		y						58
		illegalBits			0
	)

	(method (init)
		(super init)
		(self
			setScript		ComicScript,
			setCycle			Walk,
			stopUpd,
		)
	)
)

(instance aSign of Prop
	(properties
		view 				vComedyHut
      loop				lSign
		x					223
		y					50
		cycleSpeed		1
	)
	(method (init)
		(super init)
		(self
			setPri			3,
			stopUpd,
		)
	)
)

